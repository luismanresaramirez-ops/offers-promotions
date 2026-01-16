package canalplus.offres.offres.controller.dto.request;

import java.math.BigDecimal;

public record ArticleInput(Type type, String designation, BigDecimal prix) {}
