package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.RuoloDTO;
import it.nesea.albergo.utente_ruolo.dto.request.CreaRuoloRequest;


public interface RuoloService {
    public RuoloDTO creaRuolo(CreaRuoloRequest request);
}
