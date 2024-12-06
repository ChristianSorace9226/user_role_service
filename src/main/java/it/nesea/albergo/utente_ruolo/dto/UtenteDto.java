package it.nesea.albergo.utente_ruolo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 5579835672744788931L;

    private short id;
    private String nome;
    private String cognome;
    private int idRuolo;
    private LocalDate dataCancellazione;

}
