package it.nesea.albergo.utente_ruolo.exception;

import it.nesea.albergo.utente_ruolo.dto.response.CustomResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Gestisce la violazione delle constraint di validazione come la lunghezza del nome
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> errors.add(violation.getMessage()));
        log.error("Errore di validazione: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomResponse.error(errors));
    }

    // Gestisce gli errori di validazione a livello di metodo (ad esempio annotazioni @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.add(e.getDefaultMessage()));
        log.error("Errore di validazione dei dati di input: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomResponse.error(errors));
    }

    // Gestisce le eccezioni quando il corpo della richiesta non è leggibile (ad esempio formato JSON errato)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Errore nel formato del messaggio: Corpo della richiesta mancante o non valido.");
        List<String> errors = new ArrayList<>();
        errors.add("Richiesta non corretta. Corpo della richiesta mancante o non valido.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomResponse.error(errors));
    }

    // Gestisce le violazioni generali di integrità dei dati (violazioni di vincoli)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        // Se l'errore è causato da un duplicato, restituiamo un messaggio chiaro
        if (message != null && message.contains("Duplicate entry") || message.contains("USER_ROLE.UQ_NOME_INDEX_4")) {
            log.warn("Conflitto di unicità: " + message);
            List<String> errors = new ArrayList<>();
            errors.add("Esiste già un ruolo con il nome scelto.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CustomResponse.error(errors));
        }

        // Se non è un errore di duplicato, possiamo restituire un errore generico di integrità dei dati
        log.error("Errore di integrità dei dati: " + message);
        List<String> errors = new ArrayList<>();
        errors.add("Errore di integrità dei dati, si è verificato un conflitto durante il salvataggio.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomResponse.error(errors));
    }

    // Gestisce il caso di ruoli non trovati nel DB (es. nome non valido o altro)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomResponse> handleNotFoundException(NotFoundException ex) {
        log.error("Errore: " + ex.getMessage());
        List<String> errors = new ArrayList<>();
        errors.add("Risorsa non trovata. Dettagli: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CustomResponse.error(errors));
    }

    // Gestisce i conflitti di esistenza, come tentativi di inserire dati duplicati
    @ExceptionHandler(InstanceAlreadyExistsException.class)
    public ResponseEntity<CustomResponse> handleInstanceAlreadyExistsException(InstanceAlreadyExistsException ex) {
        log.error("Conflitto di esistenza dell'istanza: " + ex.getMessage());
        List<String> errors = new ArrayList<>();
        errors.add("Conflitto di esistenza. L'istanza che stai cercando di creare esiste già.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(CustomResponse.error(errors));
    }

    // Gestisce problemi di credenziali non valide
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Credenziali errate: " + ex.getMessage());
        List<String> errors = new ArrayList<>();
        errors.add("Credenziali errate. Per favore controlla il tuo nome utente e la tua password.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CustomResponse.error(errors));
    }

    // Gestisce eccezioni generiche
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> handleGeneralException(Exception ex) {
        log.error("Errore generico: " + ex.getMessage());
        List<String> errors = new ArrayList<>();
        errors.add("Si è verificato un errore imprevisto, riprova più tardi.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CustomResponse.error(errors));
    }

    // Metodo per creare una risposta di errore
    private CustomResponse getError(Exception ex) {
        List<String> errors = new ArrayList<>(1);
        errors.add(ex.getMessage());
        return CustomResponse.error(errors);
    }
}
