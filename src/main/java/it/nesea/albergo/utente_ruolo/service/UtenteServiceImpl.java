package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.exception.NotFoundException;
import it.nesea.albergo.utente_ruolo.mapper.UtenteMapper;
import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import it.nesea.albergo.utente_ruolo.model.repository.RuoloRepository;
import it.nesea.albergo.utente_ruolo.model.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UtenteServiceImpl implements UtenteService {

    private final UtenteMapper utenteMapper;
    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;

    public UtenteServiceImpl(UtenteMapper utenteMapper, UtenteRepository utenteRepository, RuoloRepository ruoloRepository) {
        this.utenteMapper = utenteMapper;
        this.utenteRepository = utenteRepository;
        this.ruoloRepository = ruoloRepository;
    }

    @Override
    public void createUtente(CreaUtenteDto creaUtenteDto) {
        log.info("Creazione utente: {}", creaUtenteDto);
        Utente utente = utenteMapper.toEntity(creaUtenteDto);
        Ruolo ruolo = ruoloRepository.findById(creaUtenteDto.getIdRuolo())
                .orElseThrow(() -> new NotFoundException("Ruolo non trovato"));
        utente.setRuolo(ruolo);
        utenteRepository.save(utente);
    }

    public void canellaUtente(short idUtente){
        log.info("Cancellazione utente [{}] iniziata", idUtente);
        Utente utente = utenteRepository.findById(idUtente)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
        utente.setCancellato(true);
        utenteRepository.save(utente);
    }
}
