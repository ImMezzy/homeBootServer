package rut.home.tournaments.service.exceptions;

public class ParticipantLimitException extends RuntimeException {
    public ParticipantLimitException() {
        super("Максимальное количество не может быть меньше числа зарегистрированных участников");
    }
}
