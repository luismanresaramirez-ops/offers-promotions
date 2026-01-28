package canalplus.offres.offres.controller.dto.request;

import java.time.LocalDate;

public record DealInput(Long numContract, Long articleId, LocalDate dateDebut, LocalDate finAbonement, LocalDate effectiveDate) {

}
