package it.nesea.albergo.utente_ruolo.model.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "RUOLO", schema = "USER_ROLE")
public class Ruolo implements Serializable {
    @Serial
    private static final long serialVersionUID = 8007514805587848964L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ruoloGenerator")
    @SequenceGenerator(name = "ruoloGenerator", schema = "user_role", sequenceName = "seq_ruolo", allocationSize = 1)
    @Column(name = "ID", nullable = false, precision = 2)
    private int id;

    @Column(name = "NOME", nullable = false, length = 20)
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}