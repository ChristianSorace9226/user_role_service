package it.nesea.albergo.utente_ruolo.model.repository;

import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Integer> {
    Ruolo findByNomeIgnoreCase(String nome);
}
