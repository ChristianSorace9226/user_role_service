package it.nesea.albergo.utente_ruolo.controller;

import it.nesea.albergo.common_lib.dto.response.CustomResponse;
import it.nesea.albergo.utente_ruolo.dto.request.RicercaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.response.UtenteDto;
import it.nesea.albergo.utente_ruolo.service.UtenteService;
import it.nesea.albergo.utente_ruolo.service.UtilService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/util")
public class UtilController {
    private final UtilService utilService;

    public UtilController(UtilService utilService) {
        this.utilService = utilService;
    }

    @GetMapping("/exists")
    public ResponseEntity<CustomResponse<Boolean>> getUtenteById(@RequestParam Integer idUtente) {
        return ResponseEntity.ok(CustomResponse.success(utilService.isUtentePresente(idUtente)));
    }
}
