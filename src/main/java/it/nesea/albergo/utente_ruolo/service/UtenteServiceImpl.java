package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.ModUtenteDto;
import it.nesea.albergo.utente_ruolo.exception.NotFoundException;
import it.nesea.albergo.utente_ruolo.mapper.UtenteMapper;
import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import it.nesea.albergo.utente_ruolo.model.repository.RuoloRepository;
import it.nesea.albergo.utente_ruolo.model.repository.UtenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


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
    public UtenteDto createUtente(CreaUtenteDto creaUtenteDto) {
        log.info("Creazione utente: {}", creaUtenteDto);
        Utente utente = utenteMapper.fromCreaUtenteDtoToEntity(creaUtenteDto);
        Ruolo ruolo = ruoloRepository.findById(creaUtenteDto.getIdRuolo())
                .orElseThrow(() -> new NotFoundException("Ruolo non trovato"));
        utente.setRuolo(ruolo);
        utente.setDataCancellazione(null);
        utenteRepository.save(utente);
        return utenteMapper.entityToDto(utente);
    }

    @Override
    public LocalDate cancellaUtente(short id) {
        log.info("Cancellazione utente [{}] iniziata", id);
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
        utente.setDataCancellazione(LocalDate.now());
        utenteRepository.save(utente);
        return utente.getDataCancellazione();
    }

    @Override
    public UtenteDto modificaUtente(ModUtenteDto modUtenteDto, short id) {
        log.info("Modifica utente [{}]", id);
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
        if (modUtenteDto.getNome() != null) {
            utente.setNome(modUtenteDto.getNome());
            log.info("Nome modificato");
        }
        if (modUtenteDto.getCognome() != null) {
            utente.setCognome(modUtenteDto.getCognome());
            log.info("Cognome modificato");
        }
        if (modUtenteDto.getIdRuolo() != null) {
            Ruolo ruolo = ruoloRepository.findById(modUtenteDto.getIdRuolo())
                    .orElseThrow(() -> new NotFoundException("Ruolo non trovato"));
            utente.setRuolo(ruolo);
            log.info("Ruolo modificato");
        }
        utenteRepository.save(utente);
        return utenteMapper.entityToDto(utente);
    }
}
