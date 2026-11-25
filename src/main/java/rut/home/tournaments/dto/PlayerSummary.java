package rut.home.tournaments.dto;

import java.util.UUID;

public record PlayerSummary (
        UUID id,
        String username,
        Integer rating,
        String tournamentTitle,
        Integer seed
) {
}