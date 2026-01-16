package canalplus.offres.offres.controller.dto.response;

import java.math.BigDecimal;

import canalplus.offres.offres.domain.model.Promotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record PromotionResult(
		Long id,
        boolean enPromotion,
        BigDecimal prixInitial,
        BigDecimal prixFinal,
        MoisGratuit moisGratuit,
        PromotionSummary promotionAppliquee
) {}