package pl.mygroup.ScienceConference.article;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mygroup.ScienceConference.review.ReviewDTO;
import pl.mygroup.ScienceConference.review.ReviewService;
import pl.mygroup.ScienceConference.security.config.WebSecurityConfig;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/article")
public class ArticleViewController {

    private final ArticleService articleService;
    private final WebSecurityConfig webSecurityConfig;
    private final ReviewService reviewService;

    @GetMapping
    public String getArticles(Model model){
        List<ArticleDTO> articleDTOS = articleService.getArticles();
        if(articleDTOS.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("articles", articleDTOS);
        model.addAttribute("isLoggedIn", webSecurityConfig.isLoggedIn());
        return "article";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable Long id,
                             Model model) {
        model.addAttribute("isLoggedIn", webSecurityConfig.isLoggedIn());
        ArticleDTO articleDTO = articleService.getArticle(id);
        if (articleDTO == null) {
            return "redirect:/";
        }
        List<ReviewDTO> reviews = reviewService.getReviewsByArticle(id);
        model.addAttribute("articleDTO", articleDTO);
        model.addAttribute("reviews", reviews);
        return "articleDetails";
    }

    @GetMapping("/edit/{id}")
    public String editArticle(@PathVariable Long id,
                              Model model) {
        ArticleDTO articleDTO = articleService.getArticle(id);
        if (articleDTO == null) {
            return "redirect:/";
        }
        model.addAttribute("articleDTO", articleDTO);
        return "editArticle";
    }

    @GetMapping("/add")
    public String addArticle(Model model){
        if(!SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .contains(new SimpleGrantedAuthority("ADMIN"))){
            model.addAttribute("errorMessage", "Musisz mieć uprawnienia administratora, aby móc dodać nowy artykuł!");
            return getArticles(model);
        }

        model.addAttribute("articleDTO", new ArticleDTO());
        return "addArticle";
    }

    @GetMapping("/addReview/{articleId}")
    public String addReview(@PathVariable Long articleId,
                            Model model){
        model.addAttribute("reviewDTO", new ReviewDTO());
        model.addAttribute("articleId", articleId);
        return "addReviewToArticle";
    }

    @PostMapping("/addReview/{articleId}")
    public String createReview(@PathVariable Long articleId,
                               @ModelAttribute ReviewDTO reviewDTO){
        reviewService.createReview(reviewDTO, articleId);
        return "redirect:/article/{articleId}";
    }

    @PostMapping("/add")
    public String createArticle(@ModelAttribute ArticleDTO articleDTO){
        articleService.createArticle(articleDTO);
        return "redirect:/article";
    }

    @PostMapping("/edit/{id}")
    public String getNewArticle(@PathVariable Long id,
                                @ModelAttribute ArticleDTO articleDTO) {
        return updateArticle(id, articleDTO);
    }

    @PutMapping("/edit/{id}")
    public String updateArticle(@PathVariable Long id,
                                ArticleDTO articleDTO) {
        articleService.updateArticle(id, articleDTO);
        return "redirect:/article/{id}";
    }

    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id){
        return removeArticle(id);
    }

    @DeleteMapping("/delete/{id}")
    public String removeArticle(@PathVariable Long id){
        articleService.removeArticle(id);
        return "redirect:/article";
    }

}
