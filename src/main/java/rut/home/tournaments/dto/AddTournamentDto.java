package rut.home.tournaments.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class AddTournamentDto {
    @NotBlank(message = "Введите название турнира")
    @Size(max = 100, message = "Не более 100 символов")
    private String title;

    @NotNull(message = "Введите максимальное количество участников")
    @Min(value = 2, message = "От 2-ух участников")
    @Max(value = 64, message = "До 64 участников")
    private Integer maxParticipants;

    @NotNull(message = "Введите призовой фонд")
    @Min(value = 1000, message = "От 1000 рублей")
    @Max(value = 100000000, message = "До 100000000 рублей")
    private Integer prizeFund;

    @NotNull(message = "Введите дату и время начала")
    private LocalDateTime dateTime;

    @NotBlank(message = "Введите место проведения")
    @Size(max = 100, message = "Не более 100 символов")
    private String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Integer getPrizeFund() {
        return prizeFund;
    }

    public void setPrizeFund(Integer prizeFund) {
        this.prizeFund = prizeFund;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
