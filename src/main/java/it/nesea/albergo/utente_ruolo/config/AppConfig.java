package it.nesea.albergo.utente_ruolo.config;

import it.nesea.albergo.common_lib.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
