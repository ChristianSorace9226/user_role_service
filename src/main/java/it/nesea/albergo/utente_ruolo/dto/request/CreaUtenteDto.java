package it.nesea.albergo.utente_ruolo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CreaUtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7646631177953932401L;

    @NotBlank(message = "il nome non puo essere vuoto e non puo essere null")
    @Size(min = 2, max = 50, message = "il nome non puo contenere meno di 2 caratteri e non puo essere lungo più di 50")
    private String nome;

    @NotBlank(message = "il cognome non può essere vuoto e non può essere null")
    @Size(min = 2, max = 50, message = "il cognome non può contenere meno di 2 caratteri e non può essere lungo più di 50")
    private String cognome;

    @Min(value = 1, message = "Il campo id ruolo deve essere maggiore o uguale a 1")
    private int idRuolo;
}