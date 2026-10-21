package rut.home.tournaments.service;

import org.springframework.stereotype.Service;
import rut.home.tournaments.model.Tournament;
import rut.home.tournaments.repository.TournamentRepository;
import rut.home.tournaments.service.exceptions.TournamentNotFoundException;

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
    public Tournament getTournamentById(int tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException(tournamentId));
    }
}
