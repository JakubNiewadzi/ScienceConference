package pl.mygroup.ScienceConference.review;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/review")
public class ReviewViewController {

    private final ReviewService reviewService;

    @GetMapping("/edit/{id}")
    public String editReview(@PathVariable Long id, Model model){
        Optional<ReviewDTO> reviewDTOOptional = reviewService.getReview(id);
        if(reviewDTOOptional.isEmpty()){
            return "redirect:/article";
        }
        model.addAttribute("reviewDTO", reviewDTOOptional.get());
        return "editReview";
    }

    @PostMapping("/edit/{id}")
    public String getNewReview(@PathVariable Long id, @ModelAttribute ReviewDTO reviewDTO){
        return updateReview(id, reviewDTO);
    }

    @PutMapping("/edit/{id}")
    public String updateReview(@PathVariable Long id, ReviewDTO reviewDTO){
        reviewService.updateReview(id, reviewDTO);
        return "redirect:/article";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable Long id){
        return removeReview(id);
    }

    @DeleteMapping("/delete/{id}")
    public String removeReview(@PathVariable Long id){
        reviewService.removeReview(id);
        return "redirect:/article";
    }

}
