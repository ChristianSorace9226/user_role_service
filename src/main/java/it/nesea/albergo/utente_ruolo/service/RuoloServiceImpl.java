package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.RuoloDTO;
import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.AssegnaRuoloRequest;
import it.nesea.albergo.utente_ruolo.dto.request.CreaRuoloRequest;
import it.nesea.albergo.utente_ruolo.exception.BadRequestException;
import it.nesea.albergo.utente_ruolo.exception.NotFoundException;
import it.nesea.albergo.utente_ruolo.mapper.RuoloMapper;
import it.nesea.albergo.utente_ruolo.mapper.UtenteMapper;
import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import it.nesea.albergo.utente_ruolo.model.repository.RuoloRepository;
import it.nesea.albergo.utente_ruolo.model.repository.UtenteRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class RuoloServiceImpl implements RuoloService {

    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;
    private final UtenteMapper utenteMapper;
    private final RuoloMapper ruoloMapper;

    @Override
    @Transactional
    public RuoloDTO creaRuolo(CreaRuoloRequest request) {
        log.info("request in input: {}", request);
        Ruolo ruolo = ruoloMapper.requestToEntity(request);
        try {
            ruolo = ruoloRepository.save(ruolo);
            log.debug("Entita' ruolo salvata su db");
            return ruoloMapper.toRuoloDTO(ruolo);
        } catch (DataIntegrityViolationException e) {
            log.warn("Ruolo già presente nel db: non è possibile aggiungere un ruolo già esistente");
            throw new BadRequestException("Esiste già un ruolo con il nome scelto");
        }
    }

    @Override
    @Transactional
    public RuoloDTO modificaRuolo(CreaRuoloRequest request, Integer id) {
        log.info("Richiesta ricevuta per la modifica del ruolo con ID: {}, Dati richiesta: {}", id, request);
        if (ruoloRepository.findById(id).isPresent()) {
            log.info("trovato ruolo con id fornito");
            Ruolo ruolo = ruoloRepository.findById(id).get();
            ruolo.setNome(request.getNome());
            ruolo = ruoloRepository.save(ruolo);
            log.info("Ruolo modificato salvato correttamente sul db");
            return ruoloMapper.toRuoloDTO(ruolo);
        } else {
            log.warn("Stai cercando di modificare un record che non esiste");
            throw new NotFoundException("Nessun ruolo trovato dato l'id");
        }
    }

    @Override
    @Transactional
    public Void cancellaRuolo(Integer id) {
        //todo: aggiungi il controllo per verificare che,prima di cancellare un ruolo,non sia assegnato ad un utente valido
        if (ruoloRepository.findById(id).isPresent()) {
            log.info("il ruolo trovato esiste nel db");
            Ruolo ruolo = ruoloRepository.findById(id).get();
            ruoloRepository.delete(ruolo);
            log.info("Ruolo cancellato correttamente");
        } else {
            log.warn("Nessun ruolo trovato con l'id selezionato");
            throw new NotFoundException("Nessun ruolo trovato dato l'id");
        }
        return null;
    }

    @Override
    public List<UtenteDto> assegnaRuolo(AssegnaRuoloRequest request) {
        log.info("Richiesta ricevuta per l'assegnazione del ruolo: {}", request);
        Integer idRuolo = request.getIdRuolo();
        Ruolo ruolo = ruoloRepository.findById(idRuolo).orElseThrow(() ->
                new NotFoundException("Ruolo non trovato per id: " + idRuolo));
        List<Utente> utenti = utenteRepository.findAllById(request.getIdsUtente());
        if (utenti.isEmpty()) {
            log.warn("lista utenti vuota: nessun utente trovato con i rispettivi id");
            throw new NotFoundException("Nessun utente trovato con gli ID forniti.");
        }
        utenti.forEach(utente -> utente.setRuolo(ruolo));
        utenteRepository.saveAll(utenti);
        log.info("lista utenti aggiornata con i ruoli salvata correttamente nel db");
        return utenti.stream()
                .map(utenteMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Object ricercaRuolo(String nome) {
        if (nome != null && !nome.isEmpty()) {
            log.info("ricerca ruolo per nome {}",nome);
            Ruolo ruolo = ruoloRepository.findByNome(nome);
            if (ruolo != null) {
                return ruoloMapper.toRuoloDTO(ruolo);
            } else {
                StringBuilder sb = new StringBuilder().append("ruolo ").append(nome).append(" non trovato");
                log.warn(sb.toString());
                throw new NotFoundException(sb.toString());
            }
        } else {
            log.info("ricerca tutti i ruoli");
            List<Ruolo> ruoli = ruoloRepository.findAll();
            return ruoli.stream()
                    .map(ruoloMapper::toRuoloDTO)
                    .collect(Collectors.toList());
        }
    }
}