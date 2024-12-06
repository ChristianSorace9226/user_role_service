package it.nesea.albergo.utente_ruolo.controller;

import it.nesea.albergo.utente_ruolo.dto.RuoloDTO;
import it.nesea.albergo.utente_ruolo.dto.request.CreaRuoloRequest;
import it.nesea.albergo.utente_ruolo.dto.response.CustomResponse;
import it.nesea.albergo.utente_ruolo.service.RuoloService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/ruolo")
@AllArgsConstructor
@Validated
public class RuoloController {

    private final RuoloService ruoloService;

    //@RequestHeader("Authorization") String token,
    @PostMapping("/crea-ruolo")
    public ResponseEntity<CustomResponse<RuoloDTO>> creaRuolo(@Valid @RequestBody CreaRuoloRequest request) {
        return ResponseEntity.ok(CustomResponse.success(ruoloService.creaRuolo(request)));
    }

    @PutMapping("/modifica-ruolo/{id}")
    public ResponseEntity<CustomResponse<RuoloDTO>> modificaRuolo(@Valid @RequestBody CreaRuoloRequest request,
                                                                  @PathVariable Integer id) {
        return ResponseEntity.ok(CustomResponse.success(ruoloService.modificaRuolo(request, id)));
    }

    @DeleteMapping("/cancella-ruolo/{id}")
    public ResponseEntity<CustomResponse<Void>> cancellaRuolo(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(CustomResponse.success(ruoloService.cancellaRuolo(id)));
    }


}
