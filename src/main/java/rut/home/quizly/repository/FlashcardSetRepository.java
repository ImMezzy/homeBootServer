package rut.home.quizly.repository;

import rut.home.quizly.model.FlashcardSet;

import java.util.List;
import java.util.Optional;

public interface FlashcardSetRepository {

    List<FlashcardSet> findAll();

    Optional<FlashcardSet> findById(int id);

}
