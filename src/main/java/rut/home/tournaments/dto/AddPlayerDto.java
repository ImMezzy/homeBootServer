package rut.home.tournaments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddPlayerDto {

    @NotBlank(message = "Введите юзернейм")
    @Size(max = 100, message = "Не более 100 символов")
    private String username;

    @NotNull(message = "Введите рейтинг")
    @Min(value = 0, message = "Рейтинг не может быть отрицательным")
    private Integer rating;

    @NotBlank(message = "Выберите турнир")
    @Size(max = 100, message = "Не более 100 символов")
    private String tournamentTitle;

    public String getTournamentTitle() {
        return tournamentTitle;
    }

    public void setTournamentTitle(String tournamentTitle) {
        this.tournamentTitle = normalize(tournamentTitle);
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = normalize(username); }

    public Integer getRating() { return rating; }

    public void setRating(Integer rating) { this.rating = rating; }

    private String normalize(String value) { return value == null ? null : value.trim(); }
}
