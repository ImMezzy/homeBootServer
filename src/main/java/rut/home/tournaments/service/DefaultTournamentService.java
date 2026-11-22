package rut.home.tournaments.service;

import org.springframework.stereotype.Service;
import rut.home.tournaments.dto.AddTournamentDto;
import rut.home.tournaments.model.Tournament;
import rut.home.tournaments.repository.TournamentRepository;
import rut.home.tournaments.service.exceptions.TournamentNotFoundException;
import rut.home.tournaments.service.exceptions.ParticipantLimitException;

import java.util.List;

@Service
public class DefaultTournamentService implements TournamentService {

    private final TournamentRepository tournamentRepository;

    public DefaultTournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament tournamentDetails(String tournamentTitle) {
        return tournamentRepository.findAll().stream()
                .filter(tournament -> tournament.title().equals(tournamentTitle))
                .findFirst()
                .orElseThrow(() -> new TournamentNotFoundException(tournamentTitle));
    }

    @Override
    public Tournament getTournamentByName(String name) {
        return tournamentRepository.findByName(name)
                .orElseThrow(() -> new TournamentNotFoundException(name));
    }

    @Override
    public void addTournament(AddTournamentDto source) {
        String normalizedTitle = source.getTitle().trim();
        if (tournamentRepository.existsByName(normalizedTitle)) {
            throw new IllegalArgumentException(
                    "Турнир с таким именем уже существует"
            );
        }

        tournamentRepository.add(new Tournament(
                normalizedTitle,
                "Новый турнир",
                source.getMaxParticipants(),
                0,
                source.getPrizeFund(),
                source.getDateTime(),
                source.getLocation(),
                "Стандарт"
        ));
    }

    @Override
    public void updateTournament(String tournamentTitle, AddTournamentDto source) {
        Tournament tournament = getTournamentByName(tournamentTitle);

        if (source.getMaxParticipants() < tournament.participantCount()) {
            throw new ParticipantLimitException();
        }

        String normalizedTitle = source.getTitle().trim();

        if (!normalizedTitle.equals(tournamentTitle) && tournamentRepository.existsByName(normalizedTitle)) {
            throw new IllegalArgumentException("Турнир с таким именем уже существует");
        }

        Tournament updatedTournament = new Tournament(
                normalizedTitle,
                tournament.description(),
                source.getMaxParticipants(),
                tournament.participantCount(),
                source.getPrizeFund(),
                source.getDateTime(),
                source.getLocation(),
                tournament.format()
        );

        tournamentRepository.update(tournamentTitle, updatedTournament);
    }

    @Override
    public void deleteTournament(String title) {
        tournamentRepository.delete(title);
    }

}
