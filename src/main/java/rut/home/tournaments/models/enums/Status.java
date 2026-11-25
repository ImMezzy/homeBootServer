package rut.home.tournaments.models.enums;

public enum Status {
    REGISTRATION("Регистрация"),
    IN_PROGRESS("Идёт"),
    FINISHED("Завершён");

    private final String status;

    Status(String status) { this.status = status; }

    public String getStatus() { return status; }
}
