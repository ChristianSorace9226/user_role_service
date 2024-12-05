package it.nesea.albergo.utente_ruolo.model.repository;

import it.nesea.albergo.utente_ruolo.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Short> {
    boolean existsByNome(String nome);
}
