package canalplus.offres.offres.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import canalplus.offres.offres.controller.dto.request.ArticleInput;
import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.domain.model.Article;

@Mapper(componentModel = "spring", uses = MineurMapper.class)
public interface ArticleMapper {

    @Mapping(source = "prix", target = "price")
    @Mapping(source = "articleMineurs", target = "mineurs", qualifiedByName = "fromArticleMineurs")
    ArticleResult toResult(Article article);

    List<ArticleResult> toResults(List<Article> articles);

    @Mapping(target = "articleMineurs", ignore = true)
    @Mapping(target = "promoArticles", ignore = true)
    Article toEntity(ArticleInput input);
}
