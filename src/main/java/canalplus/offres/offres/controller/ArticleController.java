package canalplus.offres.offres.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import canalplus.offres.offres.controller.dto.request.ArticleInput;
import canalplus.offres.offres.controller.dto.request.LinkInput;
import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.controller.dto.response.PromotionResult;
import canalplus.offres.offres.service.impl.ArticleService;
import canalplus.offres.offres.service.impl.PromotionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/offers-promotions/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final PromotionService promotionService;
    private final ArticleService articleService;

    /**
     * Vérifie les promotions applicables à un article à une date donnée
     *
     * Exemple :
     * GET /api/promotions/article/1?date=2026-01-15
     */
    @GetMapping("/{articleId}/promo")
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
    public ResponseEntity<List<ArticleResult>> afficher(
            @RequestParam(defaultValue = "MAJOR") String type
    ) {
        final var result = articleService.afficher(type);
        return ResponseEntity.ok(result);
    }

    
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResult> afficher(@PathVariable Long articleId,  @RequestParam(defaultValue = "MAJOR") String type) {
        final var result = articleService.afficher(type).stream().filter(x -> x.id().equals(articleId)).findFirst().orElseThrow();
        return ResponseEntity.ok(result);
    }
    
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ArticleInput article) {
        articleService.create(article);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/bind")
    public ResponseEntity<Void> associate(@RequestBody LinkInput article) {
        articleService.createLink(article.majeur(), article.mineur());
        return ResponseEntity.ok().build();
    }
    
}
