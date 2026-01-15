package canalplus.offres.offres.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.domain.model.Article;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

	List<ArticleResult> fromArticle(List<Article> article);
	
	@Mapping(source = "prix", target = "price")
	ArticleResult fromArticle(Article article);
}
