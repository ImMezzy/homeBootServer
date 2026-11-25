package rut.home.tournaments.service.exceptions;

public class PlayerLimitException extends RuntimeException {
    public PlayerLimitException() {
        super("Максимальное количество не может быть меньше числа зарегистрированных участников");
    }
}
