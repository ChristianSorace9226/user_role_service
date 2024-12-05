package it.nesea.albergo.utente_ruolo.model.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "UTENTE", schema = "USER_ROLE")
public class Utente implements Serializable {
    @Serial
    private static final long serialVersionUID = -4853865046646149642L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo idRuolo) {
        this.ruolo = idRuolo;
    }

}
