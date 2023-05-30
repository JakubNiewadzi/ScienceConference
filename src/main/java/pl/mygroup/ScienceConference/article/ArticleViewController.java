package pl.mygroup.ScienceConference.article;

import lombok.AllArgsConstructor;
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
}
