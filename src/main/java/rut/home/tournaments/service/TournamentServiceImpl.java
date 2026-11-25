package rut.home.tournaments.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rut.home.tournaments.dto.AddTournamentDto;
import rut.home.tournaments.dto.TournamentSummary;
import rut.home.tournaments.models.entities.Tournament;
import rut.home.tournaments.models.enums.Status;
import rut.home.tournaments.repository.TournamentRepository;
import rut.home.tournaments.service.exceptions.TournamentAlreadyExistsException;
import rut.home.tournaments.service.exceptions.TournamentNotFoundException;

import java.util.List;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public List<TournamentSummary> allTournaments() {
        return tournamentRepository.findAllByOrderByTitleAsc().stream()
                .map(this::toSummary).toList();
    }

    @Override
    public Page<TournamentSummary> allTournamentsPaginated(Pageable pageable) {
        return tournamentRepository.findAll(pageable).map(this::toSummary);
    }

    @Override
    public TournamentSummary tournamentDetails(UUID id) {
        return toSummary(findTournament(id));
    }

    @Override
    public TournamentSummary tournamentDetails(String tournamentTitle) {
        return toSummary(findTournament(tournamentTitle));
    }

    @Override
    @Transactional
    public TournamentSummary addTournament(AddTournamentDto form) {
        String title = form.getTitle().trim();
        if (tournamentRepository.existsByTitleIgnoreCase(title)) {
            throw new TournamentAlreadyExistsException();
        }
        Tournament tournament = new Tournament();
        tournament.setStatus(Status.REGISTRATION);
        copyFields(form, tournament);
        tournamentRepository.saveAndFlush(tournament);
        return toSummary(tournament);
    }

    @Override
    @Transactional
    public void addTournaments(List<AddTournamentDto> forms) {
        for (AddTournamentDto form : forms) {
            Tournament tournament = new Tournament();
            tournament.setStatus(Status.REGISTRATION);
            copyFields(form, tournament);
            tournamentRepository.save(tournament);
        }
        tournamentRepository.flush();
    }

    @Override
    @Transactional
    public void updateTournament(String tournamentTitle, AddTournamentDto form) {
        Tournament tournament = findTournament(tournamentTitle);
        String newTitle = form.getTitle().trim();
        if (!tournament.getTitle().equalsIgnoreCase(newTitle)
                && tournamentRepository.existsByTitleIgnoreCase(newTitle)) {
            throw new TournamentAlreadyExistsException();
        }
        copyFields(form, tournament);
        tournamentRepository.flush();
    }

    @Override
    @Transactional
    public TournamentSummary updateTournament(UUID id, AddTournamentDto form) {
        Tournament tournament = findTournament(id);
        String newTitle = form.getTitle().trim();
        if (!tournament.getTitle().equalsIgnoreCase(newTitle)
                && tournamentRepository.existsByTitleIgnoreCase(newTitle)) {
            throw new TournamentAlreadyExistsException();
        }
        copyFields(form, tournament);
        tournamentRepository.flush();
        return toSummary(tournament);
    }

    @Override
    @Transactional
    public void removeTournament(String tournamentTitle) {
        tournamentRepository.delete(findTournament(tournamentTitle));
        tournamentRepository.flush();
    }

    @Override
    @Transactional
    public void removeTournament(UUID id) {
        tournamentRepository.delete(findTournament(id));
        tournamentRepository.flush();
    }

    private Tournament findTournament(String title) {
        return tournamentRepository.findByTitle(title)
                .orElseThrow(() -> new TournamentNotFoundException(title));
    }

    private Tournament findTournament(UUID id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id.toString()));
    }

    private void copyFields(AddTournamentDto source, Tournament target) {
        target.setTitle(source.getTitle().trim());
        target.setDescription(source.getDescription().trim());
        target.setMaxPlayers(source.getMaxPlayers());
        target.setPrizeFund(source.getPrizeFund());
        target.setStartsAt(source.getStartsAt().toInstant(ZoneOffset.UTC));
    }

    private TournamentSummary toSummary(Tournament tournament) {
        return new TournamentSummary(tournament.getId(), tournament.getTitle(),
                tournament.getDescription(), tournament.getMaxPlayers(), tournament.getPlayers().size(), tournament.getPrizeFund(),
                tournament.getStartsAt(), tournament.getStatus());
    }
}
