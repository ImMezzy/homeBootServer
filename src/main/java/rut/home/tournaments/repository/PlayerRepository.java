package rut.home.tournaments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rut.home.tournaments.models.entities.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    List<Player> findAllByOrderByRatingDescUsernameAsc();
}
