--liquibase formatted sql

--changeset offres:init-schema
CREATE SCHEMA IF NOT EXISTS offres;

SET search_path TO offres;

CREATE TABLE article (
    carticle      BIGSERIAL PRIMARY KEY,
    designation   VARCHAR(255) NOT NULL,
    prix          NUMERIC(10,2) NOT NULL CHECK (prix >= 0)
);

CREATE TABLE promotion (
    cpromo       BIGSERIAL PRIMARY KEY,
    libelle      VARCHAR(255) NOT NULL,
    date_debut   DATE NOT NULL,
    date_fin     DATE NOT NULL,
    CHECK (date_fin >= date_debut)
);

CREATE TABLE cod_gratuit (
    cgratuit      BIGSERIAL PRIMARY KEY,
    lgratuit      VARCHAR(255),
    indplus       BOOLEAN DEFAULT FALSE,
    indsusp       BOOLEAN DEFAULT FALSE,
    indflag       BOOLEAN DEFAULT FALSE,
    cposte        NUMERIC(10,2),
    indfinabo     BOOLEAN DEFAULT FALSE,
    cqualificpt  VARCHAR(100)
);

CREATE TABLE promo_article (
    id_promo_art BIGSERIAL PRIMARY KEY,
    cpromo       BIGINT NOT NULL REFERENCES promotion(cpromo),
    carticle     BIGINT NOT NULL REFERENCES article(carticle),
    reduc        NUMERIC(5,2),
    remise       NUMERIC(10,2),
    CHECK (
        (reduc IS NOT NULL AND remise IS NULL)
        OR
        (reduc IS NULL AND remise IS NOT NULL)
    )
);

CREATE TABLE avant_gratuit (
    id_avant_gratuit BIGSERIAL PRIMARY KEY,
    cpromo           BIGINT NOT NULL REFERENCES promotion(cpromo),
    cgratuit         BIGINT NOT NULL REFERENCES cod_gratuit(cgratuit),
    num_mois         INTEGER NOT NULL,
    nbr_mois         INTEGER NOT NULL CHECK (nbr_mois > 0)
);

CREATE TABLE abo_gratuit (
    id_abo_gratuit BIGSERIAL PRIMARY KEY,
    num_contrat    BIGINT NOT NULL,
    cgratuit       BIGINT NOT NULL REFERENCES cod_gratuit(cgratuit),
    date_debut     DATE NOT NULL,
    date_fin       DATE,
    CHECK (date_fin IS NULL OR date_fin >= date_debut)
);

