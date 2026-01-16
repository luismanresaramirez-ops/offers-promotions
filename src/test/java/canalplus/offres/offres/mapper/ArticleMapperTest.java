package canalplus.offres.offres.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import canalplus.offres.offres.controller.dto.request.ArticleInput;
import canalplus.offres.offres.controller.dto.request.Type;
import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.controller.dto.response.MineurResult;
import canalplus.offres.offres.domain.model.Article;
import canalplus.offres.offres.domain.model.ArticleMineur;
import canalplus.offres.offres.domain.model.ArticleMineurId;
import canalplus.offres.offres.domain.model.Mineur;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ArticleMapperImpl.class,
        MineurMapperImpl.class
})
class ArticleMapperTest {

    @Autowired
    private ArticleMapper mapper;

    @Test
    void should_map_article_to_articleResult_without_mineurs() {
        // given
        Article article = Article.builder()
                .id(1L)
                .designation("Article test")
                .prix(new BigDecimal("19.99"))
                .articleMineurs(Set.of())
                .build();

        // when
        ArticleResult result = mapper.toResult(article);

        // then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.designation()).isEqualTo("Article test");
        assertThat(result.price()).isEqualByComparingTo("19.99");
        assertThat(result.mineurs()).isEmpty();
    }

    @Test
    void should_map_article_with_mineurs() {
        // given
        Mineur mineur = Mineur.builder()
                .id(10L)
                .designation("Option Sport")
                .prix(new BigDecimal("5.99"))
                .build();

        Article article = Article.builder()
                .id(1L)
                .designation("Abonnement")
                .prix(new BigDecimal("19.99"))
                .build();

        ArticleMineur articleMineur = ArticleMineur.builder()
                .id(new ArticleMineurId(1L, 10L))
                .article(article)
                .mineur(mineur)
                .build();

        article.setArticleMineurs(Set.of(articleMineur));

        // when
        ArticleResult result = mapper.toResult(article);

        // then
        assertThat(result.mineurs()).hasSize(1);

        MineurResult mineurResult = result.mineurs().get(0);
        assertThat(mineurResult.id()).isEqualTo(10L);
        assertThat(mineurResult.designation()).isEqualTo("Option Sport");
        assertThat(mineurResult.prixEnOption()).isEqualByComparingTo("5.99");
    }

    @Test
    void should_map_articles_list() {
        // given
        Article article1 = Article.builder()
                .id(1L)
                .designation("A1")
                .prix(new BigDecimal("10.00"))
                .build();

        Article article2 = Article.builder()
                .id(2L)
                .designation("A2")
                .prix(new BigDecimal("20.00"))
                .build();

        // when
        List<ArticleResult> results = mapper.toResults(List.of(article1, article2));

        // then
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting(ArticleResult::id)
                .containsExactly(1L, 2L);
    }

    @Test
    void should_map_articleInput_to_article_entity() {
        // given
    	ArticleInput input = new ArticleInput(
    		    Type.MAJEUR, "Article input",
    		    new BigDecimal("15.00")
    		);


        // when
        Article article = mapper.toEntity(input);

        // then
        assertThat(article).isNotNull();
        assertThat(article.getDesignation()).isEqualTo("Article input");
        assertThat(article.getPrix()).isEqualByComparingTo("15.00");
        assertThat(article.getArticleMineurs()).isNull();
        assertThat(article.getPromoArticles()).isNull();
    }
}
