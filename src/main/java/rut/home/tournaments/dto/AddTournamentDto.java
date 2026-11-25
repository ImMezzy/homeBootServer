package rut.home.tournaments.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class AddTournamentDto {
    @NotBlank(message = "Введите название турнира")
    @Size(max = 100, message = "Не более 100 символов")
    private String title;

    @NotBlank(message = "Введите описание турнира")
    @Size(max = 2000, message = "Не более 2000 символов")
    private String description;

    @NotNull(message = "Введите максимальное количество участников")
    @Min(value = 2, message = "От 2-ух участников")
    @Max(value = 64, message = "До 64 участников")
    private Integer maxPlayers;

    @NotNull(message = "Введите призовой фонд")
    @DecimalMin(value = "1000.00", message = "От 1000 рублей")
    @DecimalMax(value = "100000000.00", message = "До 100000000 рублей")
    @Digits(integer = 9, fraction = 2, message = "Не более двух знаков после запятой")
    private BigDecimal prizeFund;

    @NotNull(message = "Введите дату и время начала")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startsAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public BigDecimal getPrizeFund() {
        return prizeFund;
    }

    public void setPrizeFund(BigDecimal prizeFund) {
        this.prizeFund = prizeFund;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime dateTime) {
        this.startsAt = dateTime;
    }

}
