package pl.mygroup.ScienceConference.security.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mygroup.ScienceConference.article.Article;
import pl.mygroup.ScienceConference.article.ArticleRepository;
import pl.mygroup.ScienceConference.conference.Conference;
import pl.mygroup.ScienceConference.conference.ConferenceRepository;
import pl.mygroup.ScienceConference.panel.Panel;
import pl.mygroup.ScienceConference.panel.PanelRepository;
import pl.mygroup.ScienceConference.review.Review;
import pl.mygroup.ScienceConference.review.ReviewRepository;
import pl.mygroup.ScienceConference.user.User;
import pl.mygroup.ScienceConference.user.UserRepository;
import pl.mygroup.ScienceConference.user.UserRole;

@Configuration
@Component
public class StartUserConfig {

    public StartUserConfig(UserRepository repository, PasswordEncoder encoder,
                           ConferenceRepository conferenceRepository,
                           PanelRepository panelRepository,
                           ArticleRepository articleRepository,
                           ReviewRepository reviewRepository){
        User admin = new User();
        admin.setEmail("admin");
        admin.setPassword(encoder.encode("admin"));
        admin.setRole(UserRole.ADMIN);
        repository.save(admin);

        Conference conference = new Conference();
        conference.setName("Konferencja Naukowa Politechniki Warszawskiej");
        conference.setDescription("To jest pierwsza w historii konferencja naukowa organizowana przez Politechnikę Warszawską");
        conference.setOrganizer(admin);
        conferenceRepository.save(conference);
        Conference con;
        for(int i =0; i<5;i++){
            con = new Conference();
            con.setName("Konferencja nr " + i+1);
            con.setDescription("Opis nr " + i+1);
            con.setOrganizer(admin);
            conferenceRepository.save(con);
        }

        Panel panel = new Panel();
        panel.setDescription("opis pierwszego panelu");
        panel.setConference(conference);
        panelRepository.save(panel);
        Article article = new Article();
        article.setName("article");
        article.setCreator(admin);
        article.setReference("localhost:8080");
        articleRepository.save(article);
        Review review = new Review();
        review.setReviewer(admin);
        review.setReviewContent("dobre");
        review.setArticle(article);
        review.setRating(3.4);
        reviewRepository.save(review);
        review = new Review();
        review.setReviewer(admin);
        review.setReviewContent("słabe");
        review.setArticle(article);
        review.setRating(1.3);
        reviewRepository.save(review);
    }
}
