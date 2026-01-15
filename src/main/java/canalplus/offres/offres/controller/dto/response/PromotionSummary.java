package canalplus.offres.offres.controller.dto.response;

import java.time.LocalDate;

public record PromotionSummary(
        Long id,
        String libelle,
        LocalDate dateDebut,
        LocalDate dateFin
) {}
