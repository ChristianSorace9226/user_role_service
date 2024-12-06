package it.nesea.albergo.utente_ruolo.controller;

import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/cancella-utente/{id}")
    public ResponseEntity<?> cancellazioneUtente(@PathVariable int id) {
        try {
            userService.cancellaUtente((short) id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
