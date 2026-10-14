package rut.home.quizly.model;

import java.util.List;

public record FlashcardSet(
        int id,
        String title,
        String description,
        List<Flashcard> cards
) {}