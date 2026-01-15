package canalplus.offres.offres.controller.dto.response;

import java.math.BigDecimal;

public record ArticleResult (
	Long id,
	String designation,
	BigDecimal price
) {}
