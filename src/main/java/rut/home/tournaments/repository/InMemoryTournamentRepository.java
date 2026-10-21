package rut.home.tournaments.repository;

import org.springframework.stereotype.Repository;
import rut.home.tournaments.model.Tournament;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryTournamentRepository implements TournamentRepository {

    private final List<Tournament> tournaments = List.of(
            new Tournament(
                    1,
                    "UNO Open Cup",
                    "Открытый турнир для начинающих и опытных игроков. Матчи проходят по классическим правилам UNO.",
                    24,
                    32,
                    30000,
                    "15 ноября 2026, 12:00",
                    "Москва, игровой клуб «Точка»",
                    "Личная игра, на выбывание",
                    "Регистрация открыта"
            ),
            new Tournament(
                    2,
                    "Городской чемпионат UNO",
                    "Однодневный чемпионат с групповым этапом и финальной серией для лучших участников.",
                    48,
                    64,
                    75000,
                    "29 ноября 2026, 10:00",
                    "Москва, молодёжный центр «Смена»",
                    "Плей-офф",
                    "Регистрация открыта"
            ),
            new Tournament(
                    3,
                    "Студенческая лига UNO",
                    "Вечерний турнир для студентов. Победитель получает кубок лиги и денежный приз.",
                    16,
                    16,
                    15000,
                    "6 декабря 2026, 18:30",
                    "Онлайн",
                    "Круговая система",
                    "Набор завершён"
            )
    );

    @Override
    public List<Tournament> findAll() {
        return tournaments;
    }

    @Override
    public Optional<Tournament> findById(int id) {
        return tournaments.stream()
                .filter(tournament -> tournament.id() == id)
                .findFirst();
    }
}
