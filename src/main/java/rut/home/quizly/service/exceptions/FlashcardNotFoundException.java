package rut.home.quizly.service.exceptions;

public class FlashcardNotFoundException extends RuntimeException{
    public FlashcardNotFoundException(int id) {
        super("Карточка с id = " + id + " не найден");
    }
}
