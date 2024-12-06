package it.nesea.albergo.utente_ruolo.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssegnaRuoloRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5928527727138807253L;

    @NotNull(message = "idUtente non puo essere null")
    Set<Short> idsUtente;

    @NotNull(message = "il ruolo non puo essere null")
    Integer idRuolo;
}
