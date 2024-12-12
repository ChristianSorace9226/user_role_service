package it.nesea.albergo.utente_ruolo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UTENTE", schema = "USER_ROLE")
public class Utente implements Serializable {

    @Serial
    private static final long serialVersionUID = -4853865046646149642L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utenteGenerator")
    @SequenceGenerator(name = "utenteGenerator", schema = "user_role", sequenceName = "seq_utente", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 4)
    private short id;

    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;

    @Column(name = "COGNOME", nullable = false, length = 50)
    private String cognome;

    @ManyToOne
//    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_RUOLO", nullable = false)
    private Ruolo ruolo;

    @Column(name = "DATA_CANCELLAZIONE")
    private LocalDateTime dataCancellazione;

}