package it.nesea.albergo.utente_ruolo.model.repository;

import it.nesea.albergo.utente_ruolo.model.entity.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo,Byte> {
    Ruolo findByNome(String nome);
}
