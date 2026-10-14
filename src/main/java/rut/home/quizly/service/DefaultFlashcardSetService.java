package rut.home.quizly.service;

import org.springframework.stereotype.Service;
import rut.home.quizly.model.Flashcard;
import rut.home.quizly.model.FlashcardSet;
import rut.home.quizly.repository.FlashcardSetRepository;
import rut.home.quizly.service.exceptions.FlashcardNotFoundException;
import rut.home.quizly.service.exceptions.FlashcardsetNotFoundException;

import java.util.List;

@Service
public class DefaultFlashcardSetService implements FlashcardSetService {

    private final FlashcardSetRepository flashcardSetRepository;

    public DefaultFlashcardSetService(
            FlashcardSetRepository flashcardSetRepository
    ) {
        this.flashcardSetRepository = flashcardSetRepository;
    }

    @Override
    public List<FlashcardSet> getAllSets() {
        return flashcardSetRepository.findAll();
    }

    @Override
    public FlashcardSet getSetById(int setId) {
        return flashcardSetRepository.findById(setId)
                .orElseThrow(
                        () -> new FlashcardsetNotFoundException(setId)
                );
    }

    @Override
    public Flashcard getCardById(int cardId) {
        return flashcardSetRepository.findAll().stream()
                .flatMap(set -> set.cards().stream())
                .filter(card -> card.id() == cardId)
                .findFirst()
                .orElseThrow(
                        () -> new FlashcardNotFoundException(cardId)
                );
    }
}