package rut.home.tournaments.repository;

import rut.home.tournaments.model.Tournament;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository {

    List<Tournament> findAll();

    Optional<Tournament> findByName(String title);

    boolean existsByName(String title);

    void add(Tournament tournament);

    void update(String originalTitle, Tournament updatedTournament);

    void delete(String title);
}
