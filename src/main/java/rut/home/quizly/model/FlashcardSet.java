package rut.home.quizly.model;

import java.util.List;

public record FlashcardSet(
        String title,
        String description,
        List<Flashcard> cards // <-- Набор включает в себя карточки
) {}