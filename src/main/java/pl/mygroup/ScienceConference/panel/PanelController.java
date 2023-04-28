package pl.mygroup.ScienceConference.panel;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/panel")
@AllArgsConstructor
public class PanelController {

    private PanelService panelService;


    @GetMapping
    public ResponseEntity<List<PanelDTO>> getPanels(){
        List<PanelDTO> panels = panelService.getPanels();
        if(panels.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(panels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PanelDTO> getPanel(@PathVariable Long id){
        Optional<PanelDTO> panel = panelService.getPanel(id);
        if(panel.isPresent()){
            return ResponseEntity.ok(panel.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PanelDTO> removePanel(@PathVariable Long id){
        return panelService.removePanel(id);
    }

}
