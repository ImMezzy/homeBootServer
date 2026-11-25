package rut.home.tournaments.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rut.home.tournaments.dto.AddPlayerDto;
import rut.home.tournaments.dto.PlayerSummary;
import rut.home.tournaments.models.entities.Player;
import rut.home.tournaments.models.entities.Tournament;
import rut.home.tournaments.repository.PlayerRepository;
import rut.home.tournaments.repository.TournamentRepository;
import rut.home.tournaments.service.exceptions.TournamentNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TournamentRepository tournamentRepository) {
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public List<PlayerSummary> allPlayers() {
        return playerRepository.findAllByOrderByRatingDescUsernameAsc().stream()
                .map(this::toSummary).toList();
    }

    @Override
    @Transactional
    public void addPlayer(AddPlayerDto form) {
        String tournamentTitle = form.getTournamentTitle().trim();
        Tournament tournament = tournamentRepository.findByTitle(tournamentTitle)
                .orElseThrow(() -> new TournamentNotFoundException(tournamentTitle));
        Player player = new Player();
        player.setUsername(form.getUsername().trim());
        player.setRating(form.getRating());
        player.setTournament(tournament);
        playerRepository.saveAndFlush(player);
    }

    private PlayerSummary toSummary(Player player) {
        return new PlayerSummary(player.getId(), player.getUsername(),
                player.getRating(),
                player.getTournament().getTitle(),
                player.getSeed());
    }
}
