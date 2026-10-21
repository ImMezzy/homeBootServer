package rut.home.tournaments.model;

public record Tournament(
        int id,
        String title,
        String description,
        int participantCount,
        int maxParticipants,
        int prizeFund,
        String dateTime,
        String location,
        String format,
        String registrationStatus
) {}
