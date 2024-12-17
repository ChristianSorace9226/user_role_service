package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.request.AssegnaRuoloRequest;
import it.nesea.albergo.utente_ruolo.dto.request.CreaRuoloRequest;
import it.nesea.albergo.utente_ruolo.dto.response.RuoloDTO;
import it.nesea.albergo.utente_ruolo.dto.response.UtenteDto;

import java.util.List;

public interface RuoloService {
    RuoloDTO creaRuolo(CreaRuoloRequest request);

    RuoloDTO modificaRuolo(CreaRuoloRequest request, Integer id);

    Void cancellaRuolo(Integer id);

    Object ricercaRuolo(String nome);

    List<UtenteDto> assegnaRuolo(AssegnaRuoloRequest request);
}