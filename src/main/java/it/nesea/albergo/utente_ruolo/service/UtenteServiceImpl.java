package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.UtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.CreaUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.ModUtenteDto;
import it.nesea.albergo.utente_ruolo.dto.request.RicercaUtenteDto;
import it.nesea.albergo.utente_ruolo.exception.NotFoundException;
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

import java.time.LocalDate;
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

    @Override
    public List<UtenteDto> getUtenti(RicercaUtenteDto ricercaUtenteDto) {
        if (ricercaUtenteDto.getIdUtente() == null && ricercaUtenteDto.getNome() == null) {
            log.info("Ricerca utenti: nessun criterio di ricerca specificato. Inizio ricerca non filtrata");
            List<Utente> utenti = utenteRepository.findAll();
            if(utenti.isEmpty()){
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
            log.info("Ricerca utente [{}]", ricercaUtenteDto.getIdUtente());
            predicates.add(criteriaBuilder.equal(root.get("id"), ricercaUtenteDto.getIdUtente()));
        }
        if (ricercaUtenteDto.getNome() != null) {
            log.info("Ricerca utenti con nome [{}]", ricercaUtenteDto.getNome());
            predicates.add(criteriaBuilder.like(root.get("nome"), "%" + ricercaUtenteDto.getNome() + "%"));
        }
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        return utenteMapper.toDtoList(entityManager.createQuery(criteriaQuery).getResultList());
    }
}
