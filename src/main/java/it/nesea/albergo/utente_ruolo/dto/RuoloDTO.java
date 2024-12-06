package it.nesea.albergo.utente_ruolo.dto;

import jakarta.validation.constraints.NotBlank;
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
public class RuoloDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7247889719831201109L;

    @NotBlank
    Integer id;

    @NotBlank
    String nome;

}
