package canalplus.offres.offres.mapper;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import canalplus.offres.offres.controller.dto.response.MoisGratuit;
import canalplus.offres.offres.controller.dto.response.PromotionResult;
import canalplus.offres.offres.domain.model.Article;

@Mapper(componentModel = "spring")
public interface PromotionResultMapper {

    @Mapping(target = "enPromotion", constant = "false")
    @Mapping(target = "prixInitial", source = "article.prix")
    @Mapping(target = "prixFinal", source = "article.prix")
    @Mapping(target = "promotionAppliquee", ignore = true)
    PromotionResult fromArticleSansPromotion(Article article);

    @Mapping(target = "enPromotion", constant = "true")
    @Mapping(target = "prixInitial", source = "article.prix")
    @Mapping(target = "prixFinal", source = "prixFinal")
    @Mapping(target = "moisGratuit", expression = "java(toMoisGratuit(hasMoisGratuit, nbMoisGratuits))")
    @Mapping(target = "promotionAppliquee", ignore = true)
    PromotionResult fromArticleAvecPromotion(
        Article article,
        BigDecimal prixFinal,
        Boolean hasMoisGratuit,
        Integer nbMoisGratuits
    );

    default MoisGratuit toMoisGratuit(Boolean has, Integer nb) {
        return new MoisGratuit(Boolean.TRUE.equals(has), nb != null ? nb : 0);
    }
    
}
