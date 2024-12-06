package it.nesea.albergo.utente_ruolo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ModUtenteDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 6318970065276492008L;

    @Size(min = 2, max = 50)
    private String nome;

    @Size(min = 2, max = 50)
    private String cognome;

    private Integer idRuolo;
}