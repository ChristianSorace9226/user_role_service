package it.nesea.albergo.utente_ruolo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CreaUtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 7646631177953932401L;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    private String cognome;

    @Min(1)
    private int idRuolo;

}
