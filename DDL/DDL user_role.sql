CREATE SCHEMA IF NOT EXISTS user_role; 

DROP TABLE user_role.utente;
DROP TABLE user_role.ruolo;
DROP SEQUENCE user_role.seq_ruolo;
DROP SEQUENCE user_role.seq_utente;

CREATE SEQUENCE user_role.seq_ruolo
START WITH 1
INCREMENT 1
MAXVALUE 99
NOCACHE
NOCYCLE;

CREATE TABLE user_role.ruolo (
	id NUMBER(2) NOT NULL DEFAULT NEXT VALUE FOR user_role.seq_ruolo,
	nome VARCHAR(20) NOT NULL,
	CONSTRAINT pk_ruolo PRIMARY KEY(id)
);

CREATE SEQUENCE user_role.seq_utente
START WITH 1
INCREMENT 1
MAXVALUE 9999
NOCACHE
NOCYCLE;

CREATE TABLE IF NOT EXISTS user_role.utente (
	id NUMERIC(4) NOT NULL DEFAULT NEXT VALUE FOR user_role.seq_utente,
	nome VARCHAR(50) NOT NULL,
	cognome VARCHAR(50) NOT NULL,
	id_ruolo NUMERIC(2) NOT NULL,
	data_cancellazione DATE,
	CONSTRAINT pk_utente PRIMARY KEY(id),
	CONSTRAINT fk_utente_ruolo FOREIGN KEY(id_ruolo) REFERENCES user_role.ruolo(id)
);