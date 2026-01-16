package canalplus.offres.offres.controller.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ArticleResult (
	Long id,
	String designation,
	BigDecimal price,
	List<MineurResult> mineurs
) {}
