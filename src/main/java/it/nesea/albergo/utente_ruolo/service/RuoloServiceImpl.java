package it.nesea.albergo.utente_ruolo.service;

import it.nesea.albergo.utente_ruolo.dto.RuoloDTO;
import it.nesea.albergo.utente_ruolo.dto.request.CreaRuoloRequest;
import it.nesea.albergo.utente_ruolo.exception.NotFoundException;
import it.nesea.albergo.utente_ruolo.mapper.RuoloMapper;
import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import it.nesea.albergo.utente_ruolo.model.repository.RuoloRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
@AllArgsConstructor
@Slf4j
public class RuoloServiceImpl implements RuoloService {

    private final RuoloRepository ruoloRepository;
    private final RuoloMapper ruoloMapper;


    @Override
    @Transactional
    public RuoloDTO creaRuolo(CreaRuoloRequest request) {
        Ruolo ruolo = ruoloRepository.findByNome(request.getNome());
        if (ruolo == null) {
            ruolo = ruoloMapper.requestToEntity(request);
            ruolo = ruoloRepository.save(ruolo);
            log.info("Entita' ruolo salvata su db");
            return ruoloMapper.toRuoloDTO(ruolo);
        } else {
            log.warn("Ruolo già presente nel db: non e' possibile aggiungere un ruolo gia esistente");
            throw new NotFoundException("record gia presente nel db");
        }
    }

    @Override
    @Transactional
    public RuoloDTO modificaRuolo(CreaRuoloRequest request, Integer id) {
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
}
