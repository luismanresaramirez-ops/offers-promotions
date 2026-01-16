package canalplus.offres.offres.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import canalplus.offres.offres.controller.dto.response.MoisGratuitHistorique;
import canalplus.offres.offres.controller.dto.response.PromotionResult;
import canalplus.offres.offres.domain.model.AboGratuit;
import canalplus.offres.offres.domain.model.Article;
import canalplus.offres.offres.domain.model.AvantGratuit;
import canalplus.offres.offres.domain.model.PromoArticle;
import canalplus.offres.offres.domain.model.Promotion;
import canalplus.offres.offres.domain.repository.AboGratuitRepository;
import canalplus.offres.offres.domain.repository.ArticleRepository;
import canalplus.offres.offres.domain.repository.PromoArticleRepository;
import canalplus.offres.offres.mapper.PromotionResultMapper;
import canalplus.offres.offres.service.exception.ArticleNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PromotionService {

    private final ArticleRepository articleRepository;
    private final PromoArticleRepository promoArticleRepository;
    private final MoisGratuitService moisGratuitService;
    private final PromotionResultMapper promotionResultMapper;

    /**
     * Verifier la promotion sur un article
     * @param articleId
     * @param date
     * @return
     */
    public PromotionResult verifierPromotion(Long articleId, LocalDate date) {
        final var article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));

        return calculerPromotion(article, date, Boolean.TRUE)
                .orElseGet(() -> promotionResultMapper.fromArticleSansPromotion(article));
    }

    /**
     * Afficher les articles en promotions à partir de la date effective de consultation
     * @param date
     * @return
     */
    public List<PromotionResult> afficher(LocalDate date, Boolean decalant) {
        return articleRepository.findAll().stream()
                .map(article -> calculerPromotion(article, date, decalant))
                .flatMap(Optional::stream)
                .toList();
    }


    /**
     * Moteur de calcul de promotions
     * @param article
     * @param date
     * @return
     */
    private Optional<PromotionResult> calculerPromotion(Article article, LocalDate date, Boolean decalant) {

        final var promosActives = promoArticleRepository.findByArticleId(article.getId()).stream()
                .filter(pa -> isPromotionActive(pa.getPromotion(), date))
                .toList();

        if (promosActives.isEmpty()) {
            return Optional.empty();
        }

        final var meilleurePromo = meilleurePromo(promosActives, article.getPrix());
        final var prixFinal = calculerPrixFinal(article.getPrix(), meilleurePromo);
        final var promotion = meilleurePromo.getPromotion();
        
        Integer freeMonths = 0;
        if(decalant) {
        	freeMonths = moisGratuitService.getFreeMonthDecalantCount(promotion);
        } else {
        	freeMonths = moisGratuitService.getFreeMonthCount(promotion);
        }
        
        return Optional.of(
                promotionResultMapper.fromArticleAvecPromotion(
                        article,
                        prixFinal,
                        moisGratuitService.hasFreeMonths(promotion),
                        freeMonths
                )
        );
    }

    /* =========================
       RÈGLES MÉTIER
       ========================= */

    private boolean isPromotionActive(Promotion promotion, LocalDate date) {
        return !date.isBefore(promotion.getDateDebut())
                && !date.isAfter(promotion.getDateFin());
    }

    private PromoArticle meilleurePromo(List<PromoArticle> promos, BigDecimal prix) {
        return promos.stream()
                .max(Comparator.comparing(pa -> calculerRemise(pa, prix)))
                .orElseThrow();
    }

    private BigDecimal calculerPrixFinal(BigDecimal prixInitial, PromoArticle promo) {
        return prixInitial
                .subtract(calculerRemise(promo, prixInitial))
                .max(BigDecimal.ZERO);
    }

    private BigDecimal calculerRemise(PromoArticle promo, BigDecimal prix) {

        if (promo.getRemise() != null) {
            return promo.getRemise();
        }

        if (promo.getReduc() != null) {
            return prix
                    .multiply(promo.getReduc())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        }

        return BigDecimal.ZERO;
    }
}
