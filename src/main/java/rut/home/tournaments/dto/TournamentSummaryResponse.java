package rut.home.tournaments.dto;

import rut.home.tournaments.models.enums.Status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TournamentSummaryResponse(
        UUID id,
        String title,
        Integer playerCount,
        BigDecimal prizeFund,
        Status status
) {
}
