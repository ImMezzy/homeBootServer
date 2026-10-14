package rut.home.quizly.web;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rut.home.quizly.service.exceptions.FlashcardsetNotFoundException;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(FlashcardsetNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String flashcardSetNotFound(FlashcardsetNotFoundException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error/flashcardSet-not-found";
    }
}
