package it.nesea.albergo.utente_ruolo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreaRuoloRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 7359725594001871594L;

    @NotBlank(message = "il nome non può essere vuoto")
    @NotNull(message = "il nome non può essere null")
    String nome;
}
