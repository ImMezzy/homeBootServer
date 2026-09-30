package rut.home.quizly.service;

import org.springframework.stereotype.Service;
import rut.home.quizly.model.FlashcardSet;
import rut.home.quizly.repository.FlashcardSetRepository;

import java.util.List;

@Service
public class DefaultFlashcardSetService implements FlashcardSetService{

    private final FlashcardSetRepository flashcardSetRepository;

    public DefaultFlashcardSetService(FlashcardSetRepository flashcardSetRepository) {
        this.flashcardSetRepository = flashcardSetRepository;
    }

    @Override
    public List<FlashcardSet> allSets() {
        return flashcardSetRepository.findAll();
    }
}
