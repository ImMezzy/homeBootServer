package rut.home.tournaments.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import rut.home.tournaments.models.entities.Tournament;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

    List<Tournament> findAllByOrderByTitleAsc();

    Optional<Tournament> findByTitle(String name);

    boolean existsByTitleIgnoreCase(String title);
}
