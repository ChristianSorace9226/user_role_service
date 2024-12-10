package it.nesea.albergo.utente_ruolo.controller;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.ModUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.RicercaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.response.CustomResponse;
import it.nesea.albergo.utente_ruolo.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    private final UtenteService userService;

    public UtenteController(UtenteService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<UtenteDto>>> getUtenteById(@Valid @RequestBody RicercaUtenteDto ricercaUtenteDto) {
        return ResponseEntity.ok(CustomResponse.success(userService.getUtenti(ricercaUtenteDto)));
    }

    @PostMapping("/crea")
    public ResponseEntity<CustomResponse<UtenteDto>> creazioneUtente(@Valid @RequestBody CreaUtenteDto creaUtenteDto) {
        return ResponseEntity.ok(CustomResponse.success(userService.createUtente(creaUtenteDto)));
    }

    @PutMapping("/cancella/{id}")
    public ResponseEntity<CustomResponse<LocalDate>> cancellazioneUtente(@PathVariable int id) {
        return ResponseEntity.ok(CustomResponse.success(userService.cancellaUtente((short) id)));
    }

    @PutMapping("/modifica/{id}")
    public ResponseEntity<CustomResponse<UtenteDto>> modificaUtente(@Valid @RequestBody ModUtenteDto modUtenteDto, @PathVariable int id) {
        return ResponseEntity.ok(CustomResponse.success(userService.modificaUtente(modUtenteDto, (short) id)));
    }
}