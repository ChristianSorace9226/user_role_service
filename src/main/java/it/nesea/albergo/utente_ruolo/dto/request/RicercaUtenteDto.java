package it.nesea.albergo.utente_ruolo.dto.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RicercaUtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -7213805488428407346L;

    private String nome;

    private Short idUtente;
}
