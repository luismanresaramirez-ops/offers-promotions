package canalplus.offres.offres.mapper;



import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.domain.model.Article;

class ArticleMapperTest {

    private final ArticleMapper mapper = Mappers.getMapper(ArticleMapper.class);

    @Test
    void should_map_article_to_articleResult() {
        // given
        Article article = Article.builder()
                .id(1L)
                .designation("Article test")
                .prix(new BigDecimal("19.99"))
                .build();

        // when
        ArticleResult result = mapper.toResult(article);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.designation()).isEqualTo("Article test");
        assertThat(result.price()).isEqualByComparingTo("19.99");
    }

}