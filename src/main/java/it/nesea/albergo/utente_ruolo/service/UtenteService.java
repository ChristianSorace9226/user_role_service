package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.ModUtenteDto;

import java.time.LocalDate;

public interface UtenteService {
    UtenteDto createUtente(CreaUtenteDto creaUtenteDto);

    LocalDate cancellaUtente(short id);

    UtenteDto modificaUtente(ModUtenteDto modUtenteDto, short id);
}
