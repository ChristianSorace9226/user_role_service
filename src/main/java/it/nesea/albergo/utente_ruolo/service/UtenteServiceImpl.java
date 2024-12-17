package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.ModUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.RicercaUtenteDto;
import it.nesea.albergo.common_lib.exception.BadRequestException;
import it.nesea.albergo.common_lib.exception.NotFoundException;
import it.nesea.albergo.utente_ruolo.mapper.UtenteMapper;
import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import it.nesea.albergo.utente_ruolo.model.repository.RuoloRepository;
import it.nesea.albergo.utente_ruolo.model.repository.UtenteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UtenteServiceImpl implements UtenteService {

    private final UtenteMapper utenteMapper;
    private final UtenteRepository utenteRepository;
    private final RuoloRepository ruoloRepository;
    private final EntityManager entityManager;

    public UtenteServiceImpl(UtenteMapper utenteMapper, UtenteRepository utenteRepository, RuoloRepository ruoloRepository, EntityManager entityManager) {
        this.utenteMapper = utenteMapper;
        this.utenteRepository = utenteRepository;
        this.ruoloRepository = ruoloRepository;
        this.entityManager = entityManager;
    }

    @Override
    public UtenteDto createUtente(CreaUtenteDto creaUtenteDto) {
        log.info("Creazione utente: {}", creaUtenteDto);
        Utente utente = utenteMapper.fromCreaUtenteDtoToEntity(creaUtenteDto);
        Ruolo ruolo = ruoloRepository.findById(creaUtenteDto.getIdRuolo())
                .orElseThrow(() -> new NotFoundException("Ruolo non trovato"));
        utente.setRuolo(ruolo);
        utente.setDataCancellazione(null);
        utente.setNome(creaUtenteDto.getNome().toLowerCase().trim());
        utente.setCognome(creaUtenteDto.getCognome().toLowerCase().trim());
        utenteRepository.save(utente);
        return utenteMapper.entityToDto(utente);
    }

    @Override
    public LocalDateTime cancellaUtente(short id) {
        log.info("Cancellazione utente con id: {}", id);
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
        utente.setDataCancellazione(LocalDateTime.now());
        utenteRepository.save(utente);
        return utente.getDataCancellazione();
    }

    @Override
    public UtenteDto modificaUtente(ModUtenteDto modUtenteDto, short id) {
        log.info("Modifica utente con id: {}, oggetto request in input: [{}]", id, modUtenteDto);

//        if (modUtenteDto.getNome().isBlank() && modUtenteDto.getCognome().isBlank()) {
//            log.warn("L'oggetto: [{}] non può avere entrambi i campi vuoti", modUtenteDto);
//            throw new BadRequestException("Almeno un campo deve essere valorizzato");
//        } //todo: possibile release

        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));

        if (utente.getDataCancellazione() == null) {
            utente.setNome(modUtenteDto.getNome().toLowerCase().trim());
            log.info("Nome modificato");

            utente.setCognome(modUtenteDto.getCognome().toLowerCase().trim());
            log.info("Cognome modificato");

            utenteRepository.save(utente);
            return utenteMapper.entityToDto(utente);
        } else {
            log.warn("Stai tentando di modificare un utente cancellato in data: {}", utente.getDataCancellazione());
            throw new NotFoundException("Utente cancellato non modificabile");
        }
    }

    @Override
    public List<UtenteDto> getUtenti(RicercaUtenteDto ricercaUtenteDto) {
        log.info("Oggetto di ricerca ricevuto: [{}]", ricercaUtenteDto);

        if (ricercaUtenteDto.getIdUtente() == null && ricercaUtenteDto.getNome() == null) {
            log.info("Ricerca utenti: nessun criterio di ricerca specificato. Inizio ricerca non filtrata");
            List<Utente> utenti = utenteRepository.findAll();
            if (utenti.isEmpty()) {
                log.warn("Nessun utente trovato");
                throw new NotFoundException("Nessun utente trovato");
            }
            return utenteMapper.toDtoList(utenti);
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Utente> criteriaQuery = criteriaBuilder.createQuery(Utente.class);
        Root<Utente> root = criteriaQuery.from(Utente.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.isNull(root.get("dataCancellazione")));

        if (ricercaUtenteDto.getIdUtente() != null) {
            log.info("Ricerca utente {}", ricercaUtenteDto.getIdUtente());
            predicates.add(criteriaBuilder.equal(root.get("id"), ricercaUtenteDto.getIdUtente()));
        }

        if (ricercaUtenteDto.getNome() != null) {
            if (ricercaUtenteDto.getNome().trim().isEmpty()) {
                log.warn("il campo nome non può essere vuoto");
                throw new BadRequestException("Campo nome non può essere vuoto");
            }
            log.info("Ricerca utenti con nome {}", ricercaUtenteDto.getNome());
            String nomeTrimmato = ricercaUtenteDto.getNome().trim().toLowerCase();
            predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("nome")), nomeTrimmato ));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        return utenteMapper.toDtoList(entityManager.createQuery(criteriaQuery).getResultList());
    }


}
