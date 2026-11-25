package rut.home.tournaments.web;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rut.home.tournaments.dto.AddPlayerDto;
import rut.home.tournaments.models.enums.Status;
import rut.home.tournaments.repository.PlayerRepository;
import rut.home.tournaments.repository.TournamentRepository;
import rut.home.tournaments.service.PlayerService;
import rut.home.tournaments.service.TournamentService;
import rut.home.tournaments.service.exceptions.TournamentNotFoundException;

@Controller
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;
    private final TournamentService tournamentService;

    public PlayerController(PlayerService playerService, TournamentService tournamentService) {
        this.playerService = playerService;
        this.tournamentService = tournamentService;
    }

    @ModelAttribute("playerModel")
    public AddPlayerDto playerForm() { return new AddPlayerDto(); }

    @GetMapping({"", "/all"})
    public String allPlayers(Model model) {
        model.addAttribute("players", playerService.allPlayers());
        return "players-all";
    }

    @GetMapping("/add")
    public String addPlayerForm(Model model) {
        prepareForm(model);
        return "player-add";
    }

    @PostMapping("/add")
    public String addPlayer(@Valid @ModelAttribute("playerModel") AddPlayerDto form,
                            BindingResult bindingResult, Model model,
                            RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            try {
                playerService.addPlayer(form);
                redirectAttributes.addFlashAttribute("successMessage", "Игрок добавлен");
                return "redirect:/players";
            } catch (TournamentNotFoundException exception) {
                bindingResult.rejectValue("tournamentTitle", "missing", "Выбранный турнир не найден");
            } catch (DataIntegrityViolationException exception) {
                bindingResult.reject("integrity", "Игрок не сохранён. Проверьте турнир и значения полей");
            }
        }
        prepareForm(model);
        return "player-add";
    }



    private void prepareForm(Model model) {
        model.addAttribute("tournaments", tournamentService.allTournaments());
        model.addAttribute("statuses", Status.values());
    }

}
