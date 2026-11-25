package rut.home.tournaments.web;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rut.home.tournaments.service.exceptions.TournamentNotFoundException;

@ControllerAdvice
public class TournamentErrorHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String dataConflict(Model model) {
        model.addAttribute("errorMessage", "Изменение нарушает правило целостности данных.");
        return "error/data-conflict";
    }

    @ExceptionHandler(TournamentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tournamentNotFound(TournamentNotFoundException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error/tournament-not-found";
    }
}
