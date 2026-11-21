package rut.home.tournaments.service;

import rut.home.tournaments.dto.AddTournamentDto;
import rut.home.tournaments.model.Tournament;
import rut.home.tournaments.repository.TournamentRepository;

import java.util.List;

public interface TournamentService {

    List<Tournament> getAllTournaments();

    Tournament tournamentDetails(String tournamentName);

    Tournament getTournamentByName(String name);

    void addTournament(AddTournamentDto source);

    void updateTournament(String tournamentTitle, AddTournamentDto source);
}
