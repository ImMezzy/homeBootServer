package rut.home.quizly.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlashcardSetController {

    private final FlashcardSetController flashcardSetController;

    public FlashcardSetController(FlashcardSetController flashcardSetController) {
        this.flashcardSetController = flashcardSetController;
    }

    @GetMapping(value = "/", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String home() {
        return "Список карточек для заучивания";
    }
}
