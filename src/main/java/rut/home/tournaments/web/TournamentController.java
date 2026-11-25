package rut.home.tournaments.web;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rut.home.tournaments.dto.AddTournamentDto;
import rut.home.tournaments.dto.TournamentSummary;
import rut.home.tournaments.models.entities.Tournament;
import rut.home.tournaments.service.TournamentService;
import rut.home.tournaments.service.exceptions.PlayerLimitException;
import rut.home.tournaments.service.exceptions.TournamentAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @ModelAttribute("tournamentModel")
    public AddTournamentDto tournamentForm() {
        return new AddTournamentDto();
    }

    @GetMapping
    public String allTournaments(Model model) {
        model.addAttribute("tournamentInfos", tournamentService.allTournaments());
        return "tournaments-all";
    }

    @GetMapping("/{tournamentTitle}")
    public String tournamentDetails(@PathVariable String tournamentTitle, Model model) {
        model.addAttribute("tournamentDetails", tournamentService.tournamentDetails(tournamentTitle));
        return "tournament-details";
    }

    @GetMapping("/add")
    public String addTournamentForm(Model model) {
        prepareForm(model, false, null);
        return "tournament-add";
    }

    @PostMapping
    public String addTournament(
            @Valid @ModelAttribute("tournamentModel") AddTournamentDto tournamentModel,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            prepareForm(model, true, null);
            return "tournament-add";
        }

        try {
            tournamentService.addTournament(tournamentModel);
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue(
                    "title", "duplicate", exception.getMessage()
            );
            prepareForm(model, false, null);
            return "tournament-add";
        } catch (DataIntegrityViolationException exception) {
            bindingResult.reject("integrity", "Данные не сохранены." +
                    " Проверьте уникальность названия и значения полей");
            prepareForm(model, false, null);
            return "tournament-add";
        }
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Турнир '" + tournamentModel.getTitle() + "' успешно добавлен"
        );
        return "redirect:/tournaments";
    }


    @GetMapping("/{tournamentTitle}/edit")
    public String editTournamentForm(@PathVariable String tournamentTitle, Model model) {
        TournamentSummary tournament = tournamentService.tournamentDetails(tournamentTitle);
        AddTournamentDto form = new AddTournamentDto();
        form.setTitle(tournament.title());
        form.setDescription(tournament.description());
        form.setMaxPlayers(tournament.maxPlayers());
        form.setPrizeFund(tournament.prizeFund());
        form.setStartsAt(LocalDateTime.ofInstant(tournament.startsAt(), ZoneOffset.UTC));
        model.addAttribute("tournamentModel", form);
        prepareForm(model, true, tournamentTitle);
        return "tournament-add";
    }

    @PostMapping("/{tournamentTitle}")
    public String updateTournament(
            @PathVariable String tournamentTitle,
            @Valid @ModelAttribute("tournamentModel") AddTournamentDto tournamentModel,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            prepareForm(model, true, tournamentTitle);
            return "tournament-add";
        }

        try {
            tournamentService.updateTournament(tournamentTitle, tournamentModel);
        } catch (PlayerLimitException exception) {
            bindingResult.rejectValue("maxPlayers", "playerLimit", exception.getMessage());
            prepareForm(model, true, tournamentTitle);
            return "tournament-add";
        } catch (TournamentAlreadyExistsException exception) {
            bindingResult.rejectValue(
                    "title", "duplicate", exception.getMessage()
            );
            prepareForm(model, true, tournamentTitle);
            return "tournament-add";
        } catch (DataIntegrityViolationException exception) {
            bindingResult.reject("integrity", "Изменения не сохранены." +
                    " Проверьте уникальность названия и значения полей");
            prepareForm(model, true, tournamentTitle);
            return "tournament-add";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage", "Турнир успешно изменён"
        );
        redirectAttributes.addAttribute("tournamentTitle", tournamentModel.getTitle().trim());
        return "redirect:/tournaments/{tournamentTitle}";
    }

    @PostMapping("/{tournamentTitle}/delete")
    public String deleteTournament(
            @PathVariable String tournamentTitle,
            RedirectAttributes redirectAttributes
    ) {
        tournamentService.removeTournament(tournamentTitle);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Турнир удалён"
        );
        return "redirect:/tournaments";
    }

    private void prepareForm(Model model, boolean editing, String originalTitle) {
        model.addAttribute("editing", editing);
        model.addAttribute("originalTitle", originalTitle);
        model.addAttribute("formHeading", editing ? "Изменение турнира" : "Новый турнир");
        model.addAttribute("submitLabel", editing ? "Сохранить изменения" : "Создать турнир");
    }
}
