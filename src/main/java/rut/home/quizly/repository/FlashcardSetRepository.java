package rut.home.quizly.repository;

import rut.home.quizly.model.FlashcardSet;

import java.util.List;

public interface FlashcardSetRepository {

    List<FlashcardSet> findAll();

}
