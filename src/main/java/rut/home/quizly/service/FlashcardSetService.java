package rut.home.quizly.service;

import rut.home.quizly.model.Flashcard;
import rut.home.quizly.model.FlashcardSet;

import java.util.List;

public interface FlashcardSetService {

    List<FlashcardSet> getAllSets();

    Flashcard getCardById(int cardId);

    FlashcardSet getSetById(int setId);
}
