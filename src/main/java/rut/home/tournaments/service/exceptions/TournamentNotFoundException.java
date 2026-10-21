package rut.home.tournaments.service.exceptions;

public class TournamentNotFoundException extends RuntimeException {

    public TournamentNotFoundException(int id) {
        super("Турнир с id = " + id + " не найден");
    }
}
