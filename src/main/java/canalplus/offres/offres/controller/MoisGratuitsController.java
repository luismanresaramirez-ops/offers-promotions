package canalplus.offres.offres.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import canalplus.offres.offres.controller.dto.response.MoisGratuitHistorique;
import canalplus.offres.offres.service.impl.MoisGratuitService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mois-gratuits")
@RequiredArgsConstructor
public class MoisGratuitsController {
	
	private final MoisGratuitService moisService;
	
    /**
     * Permet d'afficher l'historique des mois gratuits
     *
     * Exemple : /api/mois-gratuits/{numContrat}?active=true
     * GET  
     */
    @GetMapping("/{numContrat}")
    public ResponseEntity<List<MoisGratuitHistorique>> verifierPromotion(
            @PathVariable Long numContrat,
            @RequestParam(required = false) String active
    ) {

    	Boolean value = Boolean.valueOf(active);
        final var result = moisService.afficherMoisGratuits(numContrat, value);
        return ResponseEntity.ok(result);
    }
    

}
