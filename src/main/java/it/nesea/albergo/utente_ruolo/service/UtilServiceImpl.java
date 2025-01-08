package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import it.nesea.albergo.utente_ruolo.model.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<Utente> utente = utenteRepository.findById(idShort);
        if (utente.isPresent()) {
            if (utente.get().getDataCancellazione() == null){
                log.info("L'utente con id {} esiste e non è stato cancellato", id);
                return true;
            }
            log.warn("L'utente con id {} è un utente cancellato", id);
            return false;
        }
        log.info("Nessun utente trovato con id {}", id);
        return false;
    }
}
