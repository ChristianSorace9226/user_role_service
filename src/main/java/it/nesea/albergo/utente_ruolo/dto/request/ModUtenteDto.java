package it.nesea.albergo.utente_ruolo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ModUtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -5022023092538610470L;

    @NotBlank(message = "il nome non puo essere vuoto e non puo essere null")
    @Size(min = 2, max = 50, message = "il nome non puo contenere meno di 2 caratteri e non puo essere lungo più di 50")
    private String nome;

    @NotBlank(message = "il cognome non puo essere vuoto e non puo essere null")
    @Size(min = 2, max = 50, message = "il cognome non puo contenere meno di 2 caratteri e non puo essere lungo più di 50")
    private String cognome;

}