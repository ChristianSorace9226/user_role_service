package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;

public interface UtenteService {
    void createUtente(CreaUtenteDto creaUtenteDto);
    void cancellaUtente(short id);
}
