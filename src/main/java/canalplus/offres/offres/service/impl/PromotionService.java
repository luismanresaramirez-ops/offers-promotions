package canalplus.offres.offres.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

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

    public PromotionResult verifierPromotion(Long articleId, LocalDate date) {
    	
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article introuvable"));

        List<PromoArticle> promos = promoArticleRepository.findByArticleId(articleId);

        List<PromoArticle> promosActives = promos.stream()
                .filter(pa -> isPromotionActive(pa.getPromotion(), date))
                .toList();

        if (promosActives.isEmpty()) {
            return promotionResultMapper.fromArticleSansPromotion(article);
        }

        PromoArticle meilleurePromo = promosActives.stream()
                .max(Comparator.comparing(pa -> calculerRemise(pa, article.getPrix())))
                .orElseThrow();

        BigDecimal prixFinal = article.getPrix()
                .subtract(calculerRemise(meilleurePromo, article.getPrix()))
                .max(BigDecimal.ZERO);
        
        final var hasFreeMonths = moisGratuitService.hasFreeMonths(meilleurePromo.getPromotion());
        final var freeMonths = moisGratuitService.getFreeMonthCount(meilleurePromo.getPromotion());
        
        return promotionResultMapper.fromArticleAvecPromotion(article, prixFinal, hasFreeMonths, freeMonths);
    }
    

    private boolean isPromotionActive(Promotion promotion, LocalDate date) {
        return !date.isBefore(promotion.getDateDebut())
                && !date.isAfter(promotion.getDateFin());
    }

    private BigDecimal calculerRemise(PromoArticle pa, BigDecimal prix) {

        if (pa.getRemise() != null) {
            return pa.getRemise();
        }

        if (pa.getReduc() != null) {
            return prix.multiply(pa.getReduc())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        }

        return BigDecimal.ZERO;
    }
}

