--liquibase formatted sql

--changeset offres:init-data

SET search_path TO offres;

INSERT INTO article (designation, prix) VALUES
('Abonnement Standard', 19.99),
('Abonnement Premium', 29.99);

INSERT INTO promotion (libelle, date_debut, date_fin) VALUES
('Promo rentrée', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days');

INSERT INTO cod_gratuit (lgratuit, indplus, indfinabo)
VALUES ('Mois gratuit', true, false);

INSERT INTO promo_article (cpromo, carticle, reduc)
VALUES (1, 1, 20.00);

INSERT INTO avant_gratuit (cpromo, cgratuit, num_mois, nbr_mois)
VALUES (1, 1, 1, 1);

INSERT INTO offres.abo_gratuit(
    id_abo_gratuit, num_contrat, cgratuit, date_debut, date_fin)
    VALUES (1,1,1, '2020-01-12', '2020-01-12');