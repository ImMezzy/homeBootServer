package rut.home.tournaments.service;

import rut.home.tournaments.model.Tournament;

import java.util.List;

public interface TournamentService {

    List<Tournament> getAllTournaments();

    Tournament getTournamentById(int tournamentId);
}
