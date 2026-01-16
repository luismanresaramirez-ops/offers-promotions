--liquibase formatted sql

--changeset offres:init-data

SET search_path TO offres;

INSERT INTO article (designation, prix) VALUES
('Abonnement Standard', 19.99),
('Abonnement Premium', 29.99),
('Abonnement Charme', 25.99);

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
    
INSERT INTO mineur (designation, prix) VALUES
('Option Multi-écrans', 5.00),
('Option UHD / 4K', 7.00),
('Option Sport+', 10.00),
('Option Dorcel', 6.00);

INSERT INTO article_mineur (carticle, cmineur) VALUES
-- Abonnement Standard
(1, 1), -- Multi-écrans
(1, 2), -- UHD / 4K

-- Abonnement Premium
(2, 1), -- Multi-écrans
(2, 2), -- UHD / 4K
(2, 3), -- Sport+

-- Abonnement Charme
(3, 1), -- Multi-écrans
(3, 2), -- UHD / 4K
(3, 4); -- Marc Dorcel
    