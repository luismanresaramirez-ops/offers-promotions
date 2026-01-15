package canalplus.offres.offres.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.controller.dto.response.PromotionResult;
import canalplus.offres.offres.service.impl.ArticleService;
import canalplus.offres.offres.service.impl.PromotionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;
    private final ArticleService articleService;

    /**
     * Vérifie les promotions applicables à un article à une date donnée
     *
     * Exemple :
     * GET /api/promotions/article/1?date=2026-01-15
     */
    @GetMapping("/{articleId}")
    public ResponseEntity<PromotionResult> verifierPromotion(
            @PathVariable Long articleId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {

        LocalDate dateEffective = (date != null) ? date : LocalDate.now();

        PromotionResult result = promotionService.verifierPromotion(articleId, dateEffective);

        return ResponseEntity.ok(result);
    }
    
    
    @GetMapping
    public ResponseEntity<List<ArticleResult>> verifierPromotion() {
        final var result = articleService.afficher();
        return ResponseEntity.ok(result);
    }
    
    
}
