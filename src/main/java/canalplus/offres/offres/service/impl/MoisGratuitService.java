package canalplus.offres.offres.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import canalplus.offres.offres.controller.dto.response.MoisGratuitHistorique;
import canalplus.offres.offres.domain.model.AboGratuit;
import canalplus.offres.offres.domain.model.AvantGratuit;
import canalplus.offres.offres.domain.model.CodGratuit;
import canalplus.offres.offres.domain.model.Promotion;
import canalplus.offres.offres.domain.repository.AboGratuitRepository;
import canalplus.offres.offres.domain.repository.ArticleRepository;
import canalplus.offres.offres.domain.repository.PromoArticleRepository;
import canalplus.offres.offres.mapper.PromotionResultMapper;
import canalplus.offres.offres.service.exception.ArticleNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoisGratuitService {
	
	private final AboGratuitRepository aboGratuitRepository;
	private final ArticleRepository articleRepository;
	private final PromoArticleRepository promoArticleRepository;
	
    public List<MoisGratuitHistorique> afficherMoisGratuits(Long numContrat, boolean actifs) {
    	List<AboGratuit> historique = aboGratuitRepository.findAbonnementsGratuitsActifs(numContrat, LocalDate.now(), actifs);

        return historique.stream()
                .map(ag -> new MoisGratuitHistorique(
                        ag.getCodGratuit().getLgratuit(),
                        ag.getDateDebut(),
                        ag.getDateFin()
                ))
                .toList();
    }
	
    public boolean hasFreeMonths(Promotion promotion) {
        // logique pour déterminer si la promo donne droit à des mois gratuits
        return promotion.getAvantGratuits() != null && !promotion.getAvantGratuits().isEmpty();
    }

    public Integer getFreeMonthDecalantCount(Promotion promotion) {
        return promotion.getAvantGratuits().stream()
        		.filter(ag -> ag.getCodGratuit().getIndplus().equals(Boolean.TRUE))
                .mapToInt(AvantGratuit::getNbrMois)
                .sum();
    }
    
    public Integer getFreeMonthSuspensionCount(Promotion promotion) {
        return promotion.getAvantGratuits().stream()
        		.filter(ag -> ag.getCodGratuit().getIndsusp().equals(Boolean.TRUE))
                .mapToInt(AvantGratuit::getNbrMois)
                .sum();
    }
    
    public Integer getFreeMonthCount(Promotion promotion) {
        return promotion.getAvantGratuits().stream()
                .mapToInt(AvantGratuit::getNbrMois)
                .sum();
    }

    public CodGratuit resolveCodGratuit(Long articleId, LocalDate effectiveDate) {

        final var article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        final var promosActives = promoArticleRepository.findByArticleId(article.getId()).stream()
                .filter(pa -> PromotionService.isPromotionActive(pa.getPromotion(), effectiveDate))
                .toList();

        if (promosActives.isEmpty()) {
            throw new IllegalStateException("Aucune promotion active pour l’article " + articleId);
        }

        final var meilleurePromo = PromotionService.meilleurePromo(promosActives, article.getPrix());

        return meilleurePromo.getPromotion()
                .getAvantGratuits().stream()
                .findFirst()
                .map(AvantGratuit::getCodGratuit)
                .orElseThrow(() ->
                        new IllegalStateException("Promotion active sans CodGratuit associé")
                );
    }


    public LocalDate computeDateFin(
            CodGratuit codGratuit,
            LocalDate dateDebut,
            LocalDate finAbonement
    ) {

        // indplus -> Prolonge la date de fin d'abonement
        if (Boolean.TRUE.equals(codGratuit.getIndplus())) {
            return finAbonement.plusMonths(codGratuit.getAvantGratuits().getFirst().getNbrMois());
        }



        // Cas par défaut 
        return finAbonement;
    }
    
    public LocalDate computeDateDeb(
            CodGratuit codGratuit,
            LocalDate dateDebut,
            LocalDate finAbonement
    ) {

        // indplus -> Prolonge la date de debut d'abonement ( cas à la marge pas très utilise)
        if (Boolean.TRUE.equals(codGratuit.getIndplus()) && Boolean.FALSE.equals(codGratuit.getIndfinabo())) {
            return dateDebut.plusMonths(codGratuit.getAvantGratuits().getFirst().getNumMois());
        }

        // Cas par défaut 
        return dateDebut;
    }

}
