package rut.home.quizly.service.exceptions;

public class FlashcardsetNotFoundException extends RuntimeException{

    public FlashcardsetNotFoundException(int id) {
        super("Набор карточек с id = " + id + " не найден");
    }
}
