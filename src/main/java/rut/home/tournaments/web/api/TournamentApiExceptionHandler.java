package rut.home.tournaments.web.api;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rut.home.tournaments.dto.ApiErrorResponse;
import rut.home.tournaments.service.exceptions.PlayerLimitException;
import rut.home.tournaments.service.exceptions.TournamentAlreadyExistsException;
import rut.home.tournaments.service.exceptions.TournamentNotFoundException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackageClasses = TournamentRestController.class)
public class TournamentApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TournamentNotFoundException.class)
    public ResponseEntity<Object> missing(TournamentNotFoundException exception, WebRequest request) {
        return response(HttpStatus.NOT_FOUND, "TOURNAMENT_NOT_FOUND", "Турнир не найден", request, Map.of());
    }

    @ExceptionHandler({TournamentAlreadyExistsException.class, DataIntegrityViolationException.class,
            PlayerLimitException.class})
    public ResponseEntity<Object> conflict(RuntimeException exception, WebRequest request) {
        return response(HttpStatus.CONFLICT, "DATA_CONFLICT",
                "Изменение конфликтует с существующими данными", request, Map.of());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> fields = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                fields.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(error(status, "VALIDATION_ERROR",
                "Запрос содержит некорректные поля", request, fields), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(error(status, "REQUEST_ERROR",
                "Проверьте метод, заголовки, формат и параметры запроса", request, Map.of()), headers, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> unexpected(Exception exception, WebRequest request) {
        logger.error("Непредвиденная ошибка API", exception);
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "Внутренняя ошибка сервера", request, Map.of());
    }

    private ResponseEntity<Object> response(HttpStatus status, String code, String message,
            WebRequest request, Map<String, String> fields) {
        return ResponseEntity.status(status).body(error(status, code, message, request, fields));
    }

    private ApiErrorResponse error(HttpStatusCode status, String code, String message,
            WebRequest request, Map<String, String> fields) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        HttpStatus knownStatus = HttpStatus.resolve(status.value());
        String reason = knownStatus != null ? knownStatus.getReasonPhrase() : "HTTP Error";
        return new ApiErrorResponse(Instant.now(), status.value(), reason, code, message, path, fields);
    }
}
