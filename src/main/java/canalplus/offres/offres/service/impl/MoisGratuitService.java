package canalplus.offres.offres.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import canalplus.offres.offres.controller.dto.response.MoisGratuitHistorique;
import canalplus.offres.offres.domain.model.AboGratuit;
import canalplus.offres.offres.domain.model.AvantGratuit;
import canalplus.offres.offres.domain.model.CodGratuit;
import canalplus.offres.offres.domain.model.Promotion;
import canalplus.offres.offres.domain.repository.AboGratuitRepository;
import canalplus.offres.offres.domain.repository.ArticleRepository;
import canalplus.offres.offres.domain.repository.PromoArticleRepository;
import canalplus.offres.offres.mapper.PromotionResultMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoisGratuitService {
	
	private final AboGratuitRepository aboGratuitRepository;
	
    public List<MoisGratuitHistorique> afficherMoisGratuits(Long numContrat, boolean actifs) {
    	List<AboGratuit> historique = aboGratuitRepository.findAbonnementsGratuitsActifs(numContrat, LocalDate.now(), actifs);

        return historique.stream()
                .map(ag -> new MoisGratuitHistorique(
                        ag.getCodGratuit().getLgratuit(),
                        ag.getDateDebut(),
                        ag.getDateFin()
                ))
                .toList();
    }
	
    public boolean hasFreeMonths(Promotion promotion) {
        // logique pour déterminer si la promo donne droit à des mois gratuits
        return promotion.getAvantGratuits() != null && !promotion.getAvantGratuits().isEmpty();
    }

    public int getFreeMonthCount(Promotion promotion) {
        return promotion.getAvantGratuits().stream()
                .mapToInt(AvantGratuit::getNbrMois)
                .sum();
    }
	
}
