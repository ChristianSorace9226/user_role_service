package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.model.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UtilServiceImpl implements UtilService {

    private final UtenteRepository utenteRepository;

    public UtilServiceImpl(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public boolean isUtentePresente(Integer id) {
        log.debug("Controlla se l'utente con id {} esiste", id);
        short idShort = id.shortValue();
        return utenteRepository.findById(idShort).isPresent();
    }
}
