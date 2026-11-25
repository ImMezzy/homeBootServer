package rut.home.tournaments.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "seed")
    private Integer seed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tournament_id",
            foreignKey = @ForeignKey(name = "fk_player_tournament"))
    private Tournament tournament;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
