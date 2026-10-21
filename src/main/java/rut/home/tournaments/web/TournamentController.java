package rut.home.tournaments.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rut.home.tournaments.service.TournamentService;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public String showAllTournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        return "tournaments-all";
    }

    @GetMapping("/{tournamentId}")
    public String showTournament(
            @PathVariable int tournamentId,
            Model model
    ) {
        model.addAttribute(
                "tournament",
                tournamentService.getTournamentById(tournamentId)
        );
        return "tournament";
    }
}
