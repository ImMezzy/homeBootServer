package rut.home.tournaments.service;

import rut.home.tournaments.dto.PlayerSummary;
import rut.home.tournaments.models.entities.Player;
import rut.home.tournaments.dto.AddPlayerDto;

import java.util.List;

public interface PlayerService {
    List<PlayerSummary> allPlayers();

    void addPlayer(AddPlayerDto form);
}
