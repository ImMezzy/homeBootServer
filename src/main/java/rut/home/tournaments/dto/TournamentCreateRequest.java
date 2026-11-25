package rut.home.tournaments.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TournamentCreateRequest(
        @NotBlank(message = "Введите название турнира") @Size(max = 100, message = "Не более 100 символов")
        String title,

        @NotBlank(message = "Введите описание турнира") @Size(max = 2000, message = "Не более 2000 символов")
        String description,

        @NotNull(message = "Введите максимальное количество участников") @Min(value = 2, message = "От 2-ух участников") @Max(value = 64, message = "До 64 участников")
        Integer maxPlayers,

        @NotNull(message = "Введите призовой фонд") @DecimalMin(value = "1000.00", message = "От 1000 рублей") @DecimalMax(value = "100000000.00", message = "До 100000000 рублей") @Digits(integer = 9, fraction = 2, message = "Не более двух знаков после запятой")
        BigDecimal prizeFund,

        @NotNull(message = "Введите дату и время начала") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startsAt
) {
    public TournamentCreateRequest {
        title = title == null ? null : title.trim();
        description = description == null ? null : description.trim();
    }
}
