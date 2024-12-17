package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.ModUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.RicercaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.response.UtenteDto;

import java.time.LocalDateTime;
import java.util.List;

public interface UtenteService {
    UtenteDto createUtente(CreaUtenteDto creaUtenteDto);

    LocalDateTime cancellaUtente(short id);

    UtenteDto modificaUtente(ModUtenteDto modUtenteDto, short id);

    List<UtenteDto> getUtenti(RicercaUtenteDto ricercaUtenteDto);
}
