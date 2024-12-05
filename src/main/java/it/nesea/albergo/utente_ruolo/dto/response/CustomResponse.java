package it.nesea.albergo.utente_ruolo.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomResponse<T> {
    T response;
    List<String> errorMessage;

    public static <T> CustomResponse<T> success(T response) {
        return new CustomResponse<>(response, new ArrayList<>());
    }

    public static <T> CustomResponse<T> error(List<String> errorMessage) {
        return new CustomResponse<>(null, errorMessage);
    }
}

