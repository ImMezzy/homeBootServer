package rut.home.quizly.repository;

import org.springframework.stereotype.Repository;
import rut.home.quizly.model.Flashcard;
import rut.home.quizly.model.FlashcardSet;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryFlashcardSetRepository implements FlashcardSetRepository{

    private final List<FlashcardSet> sets = List.of(
            new FlashcardSet("Spring основы", "Основные концепции", List.of(
                    new Flashcard("IoC", "Инверсия управления - принцип, при котором контейнер сам создает и внедряет зависимости"),
                    new Flashcard("Bean", "Объект, который создается и управляется Spring-контейнером")
            )),
            new FlashcardSet("Коллекции", "Интерфейсы и классы", List.of(
                    new Flashcard("List", "Упорядоченная коллекция, допускающая дубликаты"),
                    new Flashcard("Set", "Коллекция, не допускающая дублирующихся элементов")
            ))
    );

    @Override
    public List<FlashcardSet> findAll() { return sets; }
}
