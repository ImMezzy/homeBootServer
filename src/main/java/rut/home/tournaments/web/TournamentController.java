package rut.home.tournaments.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rut.home.tournaments.dto.AddTournamentDto;
import rut.home.tournaments.model.Tournament;
import rut.home.tournaments.repository.TournamentRepository;
import rut.home.tournaments.service.TournamentService;
import rut.home.tournaments.service.exceptions.ParticipantLimitException;

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

    @GetMapping("/add")
    public String addTournamentForm(Model model) {
        model.addAttribute("editing", false);
        model.addAttribute("formHeading", "Новый турнир");
        model.addAttribute("submitLabel", "Создать турнир");
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
            model.addAttribute("editing", false);
            model.addAttribute("formHeading", "Новый турнир");
            model.addAttribute("submitLabel", "Создать турнир");
            return "tournament-add";
        }

        try {
            tournamentService.addTournament(tournamentModel);
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue(
                    "title", "duplicate", exception.getMessage()
            );
            model.addAttribute("editing", false);
            model.addAttribute("formHeading", "Новый турнир");
            model.addAttribute("submitLabel", "Создать турнир");
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
        Tournament tournament = tournamentService.tournamentDetails(tournamentTitle);
        AddTournamentDto form = new AddTournamentDto();
        form.setTitle(tournament.title());
//        form.setDescription(tournament.description());
        form.setMaxParticipants(tournament.maxParticipants());
//        form.setParticipantCount(tournament.participantCount());
        form.setPrizeFund(tournament.prizeFund());
        form.setDateTime(tournament.dateTime());
        form.setLocation(tournament.location());
//        form.setFormat(tournament.format());
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
        } catch (ParticipantLimitException exception) {
            bindingResult.rejectValue("maxParticipants", "participantLimit", exception.getMessage());
            prepareForm(model, true, tournamentTitle);
            return "tournament-add";
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue(
                    "title", "duplicate", exception.getMessage()
            );
            prepareForm(model, true, tournamentTitle);
            return "tournament-add";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage", "Турнир успешно изменён"
        );
        redirectAttributes.addAttribute("name", tournamentModel.getTitle().trim());
        return "redirect:/tournaments/details";
    }

    @PostMapping("/{tournamentTitle}/delete")
    public String deleteTournament(
            @PathVariable String tournamentTitle,
            RedirectAttributes redirectAttributes
    ) {
        tournamentService.deleteTournament(tournamentTitle);
        redirectAttributes.addFlashAttribute(
                "successMessage", "Турнир удалён"
        );
        return "redirect:/tournaments";
    }

    @GetMapping
    public String showAllTournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        return "tournaments-all";
    }

    @GetMapping("/details")
    public String showTournament(
            @RequestParam String name,
            Model model
    ) {
        model.addAttribute(
                "tournament",
                tournamentService.getTournamentByName(name)
        );
        return "tournament";
    }

    private void prepareForm(Model model, boolean editing, String originalTitle) {
        model.addAttribute("editing", editing);
        model.addAttribute("originalTitle", originalTitle);
        model.addAttribute("formHeading", editing ? "Изменение турнира" : "Новый турнир");
        model.addAttribute("submitLabel", editing ? "Сохранить изменения" : "Создать турнир");
    }
}
