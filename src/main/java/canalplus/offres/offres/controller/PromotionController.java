package canalplus.offres.offres.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import canalplus.offres.offres.controller.dto.response.PromotionResult;
import canalplus.offres.offres.service.impl.ArticleService;
import canalplus.offres.offres.service.impl.PromotionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/offers-promotions/promotions")
@RequiredArgsConstructor
public class PromotionController {

	private final PromotionService promotionService;
	
    @GetMapping
    public ResponseEntity<List<PromotionResult>> afficher(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestParam(defaultValue = "true") String decalant
    ) {

    	LocalDate dateEffective = (date != null) ? date : LocalDate.now();
        final var result = promotionService.afficher(dateEffective, Boolean.valueOf(decalant));

        return ResponseEntity.ok(result);
    }
}
