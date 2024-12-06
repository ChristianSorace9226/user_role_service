package it.nesea.albergo.utente_ruolo.controller;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.response.CustomResponse;
import it.nesea.albergo.utente_ruolo.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    private final UtenteService userService;

    public UtenteController(UtenteService userService) {
        this.userService = userService;
    }

    @PostMapping("/crea-utente")
    public ResponseEntity<CustomResponse<UtenteDto>> creazioneUtente(@Valid @RequestBody CreaUtenteDto creaUtenteDto) {
        return ResponseEntity.ok(CustomResponse.success(userService.createUtente(creaUtenteDto)));
    }

    @DeleteMapping("/cancella-utente/{id}")
    public ResponseEntity<CustomResponse<LocalDate>> cancellazioneUtente(@PathVariable int id) {
        return ResponseEntity.ok(CustomResponse.success(userService.cancellaUtente((short) id)));
    }
}