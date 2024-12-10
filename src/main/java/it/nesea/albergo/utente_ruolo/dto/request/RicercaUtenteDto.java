package it.nesea.albergo.utente_ruolo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RicercaUtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7213805488428407346L;

    @Size(min = 2, max = 50)
    private String nome;

    private Short idUtente;
}