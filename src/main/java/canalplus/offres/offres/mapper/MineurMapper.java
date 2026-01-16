package canalplus.offres.offres.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import canalplus.offres.offres.controller.dto.request.ArticleInput;
import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.controller.dto.response.MineurResult;
import canalplus.offres.offres.domain.model.ArticleMineur;
import canalplus.offres.offres.domain.model.Mineur;

@Mapper(componentModel = "spring")
public interface MineurMapper {

    /* =========================
     * ENTITY -> DTO
     * ========================= */

    @Mapping(source = "prix", target = "price")
    ArticleResult toResult(Mineur mineur);

    List<ArticleResult> toResults(List<Mineur> mineurs);

    @Mapping(source = "prix", target = "prixEnOption")
    MineurResult toMineurResult(Mineur mineur);

    List<MineurResult> toMineurResults(List<Mineur> mineurs);

    /* =========================
     * RELATION
     * ========================= */

    default MineurResult fromArticleMineur(ArticleMineur articleMineur) {
        Mineur mineur = articleMineur.getMineur();
        return toMineurResult(mineur);
    }

    @Named("fromArticleMineurs")
    default List<MineurResult> fromArticleMineurs(Set<ArticleMineur> articleMineurs) {
        if (articleMineurs == null || articleMineurs.isEmpty()) {
            return List.of();
        }
        return articleMineurs.stream()
                .map(this::fromArticleMineur)
                .collect(Collectors.toList());
    }

    /* =========================
     * DTO -> Entity
     * ========================= */
     Mineur toEntity(ArticleInput input);
     

}
