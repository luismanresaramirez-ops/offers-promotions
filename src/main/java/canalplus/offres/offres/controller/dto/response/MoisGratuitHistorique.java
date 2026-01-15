package canalplus.offres.offres.controller.dto.response;

import java.time.LocalDate;

public record MoisGratuitHistorique(
	    String codeGratuit,
	    LocalDate dateDebut,
	    LocalDate dateFin
	) {}
