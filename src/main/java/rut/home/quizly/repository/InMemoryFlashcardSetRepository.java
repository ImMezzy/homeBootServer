package rut.home.quizly.repository;

import org.springframework.stereotype.Repository;
import rut.home.quizly.model.Flashcard;
import rut.home.quizly.model.FlashcardSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryFlashcardSetRepository implements FlashcardSetRepository{

    private final List<FlashcardSet> sets = List.of(
            new FlashcardSet(1,"Spring основы", "Основные концепции", List.of(
                    new Flashcard(1,"IoC", "Инверсия управления - принцип, при котором контейнер сам создает и внедряет зависимости"),
                    new Flashcard(2,"Bean", "Объект, который создается и управляется Spring-контейнером")
            )),
            new FlashcardSet(2,"Коллекции", "Интерфейсы и классы", List.of(
                    new Flashcard(3,"List", "Упорядоченная коллекция, допускающая дубликаты"),
                    new Flashcard(4,"Set", "Коллекция, не допускающая дублирующихся элементов")
            ))
    );

    @Override
    public List<FlashcardSet> findAll() { return sets; }

    @Override
    public Optional<FlashcardSet> findById(int id) {
        for (FlashcardSet set : sets) {
            if (set.id() == id) {
                return Optional.of(set);
            }
        }

        return Optional.empty();
    }
}
