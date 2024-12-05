package it.nesea.albergo.utente_ruolo.dto;

import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;

import java.io.Serial;
import java.io.Serializable;

public class UtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 5579835672744788931L;

    private short id;
    private String nome;
    private String cognome;
    private Ruolo ruolo;

}
