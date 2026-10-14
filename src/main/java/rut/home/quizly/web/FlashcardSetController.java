package rut.home.quizly.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rut.home.quizly.service.FlashcardSetService;

@Controller
@RequestMapping("/sets")
public class FlashcardSetController {

    private final FlashcardSetService flashcardSetService;

    public FlashcardSetController(FlashcardSetService flashcardSetService) {
        this.flashcardSetService = flashcardSetService;
    }

    @GetMapping
    public String showAllSets(Model model) {
        model.addAttribute("setsInfos", flashcardSetService.getAllSets());
        return "sets-all";
    }

    @GetMapping("/{setId}")
    public String showSet(
            @PathVariable int setId,
            Model model) {
        model.addAttribute(
                "set",
                flashcardSetService.getSetById(setId)
        );
        return "set";
    }

    @GetMapping("/cards/{cardNumber}")
    public String showCard(
            @PathVariable int cardNumber,
            Model model) {
        model.addAttribute(
                "card",
                flashcardSetService.getCardById(cardNumber)
        );
        return "card";
    }


}
