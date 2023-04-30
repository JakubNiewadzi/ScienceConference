package pl.mygroup.ScienceConference.panel;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mygroup.ScienceConference.article.Article;
import pl.mygroup.ScienceConference.article.ArticleRepository;
import pl.mygroup.ScienceConference.conference.Conference;
import pl.mygroup.ScienceConference.conference.ConferenceService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PanelService {

    private final PanelRepository panelRepository;
    private PanelMapper panelMapper;
    private ConferenceService conferenceService;
    private ArticleRepository articleRepository;

    public List<PanelDTO> getPanels(){
        return panelRepository.findAll()
                .stream().map(panelMapper)
                .toList();
    }

    public Optional<PanelDTO> getPanel(Long id){
        return panelRepository.findById(id)
                .map(panelMapper);
    }

    public List<PanelDTO> getPanelsByConference(Long conferenceId){
        return panelRepository.findByConferenceId(conferenceId)
                .stream().map(panelMapper)
                .toList();
    }

    public ResponseEntity<String> createPanel(Long conferenceId, PanelDTO panelDTO){
        Optional<Conference> conferenceOptional = conferenceService.getConference(conferenceId);
        if(conferenceOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(panelDTO.getDescription()==null ||
            panelDTO.getStartDate()==null ||
            panelDTO.getEndDate() ==null){
            return ResponseEntity.badRequest().build();
        }

        if(panelDTO.getEndDate().isBefore(panelDTO.getStartDate()) ||
                panelDTO.getStartDate().isBefore(LocalDateTime.now())){
            return ResponseEntity.badRequest().build();
        }

        Conference conference = conferenceOptional.get();
        Panel panel = new Panel();
        panel.setDescription(panelDTO.getDescription());
        panel.setStartDate(panelDTO.getStartDate());
        panel.setEndDate(panelDTO.getEndDate());
        panel.setConference(conference);
        panelRepository.save(panel);
        panelDTO.setConferenceName(conference.getName());
        panelDTO.setConferenceEndPoint("/api/conference/" + conference.getId());
        return ResponseEntity.ok("Panel has been successfully created");
    }

    public ResponseEntity<PanelDTO> removePanel(Long id){
        Optional<PanelDTO> removed = panelRepository.
                findById(id).map(panelMapper);
        if(removed.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        panelRepository.deleteById(id);
        return ResponseEntity.ok(removed.get());
    }

    @Transactional
    public ResponseEntity<PanelDTO> addArticleToPanel(Long panelId, Long articleId){
        Optional<Panel> panelOptional= panelRepository.findById(panelId);
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        if(panelOptional.isEmpty() ||
            articleOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Panel panel = panelOptional.get();
        Article article = articleOptional.get();
        panel.getArticles().add(article);
        article.getPanels().add(panel);
        PanelDTO panelDTO = Optional.of(panel).map(panelMapper).get();
        return ResponseEntity.ok(panelDTO);
    }

}
