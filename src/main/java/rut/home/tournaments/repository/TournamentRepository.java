package rut.home.tournaments.repository;

import rut.home.tournaments.model.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository {

    List<Tournament> findAll();

    Optional<Tournament> findById(int id);
}
