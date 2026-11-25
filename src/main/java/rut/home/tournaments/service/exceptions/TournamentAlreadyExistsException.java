package rut.home.tournaments.service.exceptions;

public class TournamentAlreadyExistsException extends RuntimeException
{
    public TournamentAlreadyExistsException()
    { super("Турнир с таким названием уже существует"); }
}
