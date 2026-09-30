package rut.home.quizly.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rut.home.quizly.model.FlashcardSet;
import rut.home.quizly.service.FlashcardSetService;

import java.util.stream.Collectors;

@Controller
public class FlashcardSetController {

    private final FlashcardSetService flashcardSetService;

    public FlashcardSetController(FlashcardSetService flashcardSetService) { this.flashcardSetService = flashcardSetService; }

    @GetMapping(value = "/", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String home() {
        return "Список карточек для заучивания";
    }

    @GetMapping(value = "/sets", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String sets() {
        return flashcardSetService.allSets().stream()
                .map(set -> set.title() + "\n"
                        + "   " + set.description() + "\n"
                        + "   Карточек: " + set.cards().size())
                .collect(Collectors.joining("\n─────────────────────\n"));
    }

    private String formatFlashcardSet(FlashcardSet flashcardSet) {
        return flashcardSet.title() + " | " + flashcardSet.description() + " | " + flashcardSet.cards();
    }
}
