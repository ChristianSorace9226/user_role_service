package it.nesea.albergo.utente_ruolo.controller;

import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    private final UtenteService userService;

    public UtenteController(UtenteService userService) {
        this.userService = userService;
    }

    @PostMapping("/crea-utente")
    public ResponseEntity<?> creazioneUtente(@Valid @RequestBody CreaUtenteDto creaUtenteDto) {
        try {
            userService.createUtente(creaUtenteDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
