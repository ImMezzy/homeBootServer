package rut.home.tournaments.dto;

import rut.home.tournaments.models.enums.Status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TournamentSummary(
        UUID id,
        String title,
        String description,
        Integer maxPlayers,
        Integer playerCount,
        BigDecimal prizeFund,
        Instant startsAt,
        Status status
) {
}
