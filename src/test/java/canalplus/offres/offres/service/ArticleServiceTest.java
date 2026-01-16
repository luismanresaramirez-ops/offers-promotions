package canalplus.offres.offres.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import canalplus.offres.offres.controller.dto.request.ArticleInput;
import canalplus.offres.offres.controller.dto.request.Type;
import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.domain.model.Article;
import canalplus.offres.offres.domain.model.ArticleMineur;
import canalplus.offres.offres.domain.model.Mineur;
import canalplus.offres.offres.domain.repository.ArticleMineurRepository;
import canalplus.offres.offres.domain.repository.ArticleRepository;
import canalplus.offres.offres.domain.repository.MineurRepository;
import canalplus.offres.offres.mapper.ArticleMapper;
import canalplus.offres.offres.mapper.MineurMapper;
import canalplus.offres.offres.service.exception.ArticleNotFoundException;
import canalplus.offres.offres.service.exception.MineurNotFoundException;
import canalplus.offres.offres.service.impl.ArticleService;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MineurRepository mineurRepository;

    @Mock
    private ArticleMineurRepository articleMineurRepository;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private MineurMapper mineurMapper;

    @InjectMocks
    private ArticleService articleService;

    /* =========================
     * afficher
     * ========================= */

    @Test
    void afficher_should_return_mineurs_when_type_is_MINEUR() {
        when(mineurRepository.findAll()).thenReturn(List.of(new Mineur()));
        when(mineurMapper.toResults(any())).thenReturn(List.of(mock(ArticleResult.class)));

        articleService.afficher("MINEUR");

        verify(mineurRepository).findAll();
        verify(mineurMapper).toResults(any());
        verifyNoInteractions(articleRepository, articleMapper);
    }

    @Test
    void afficher_should_return_articles_when_type_is_not_MINEUR() {
        when(articleRepository.findAll()).thenReturn(List.of(new Article()));
        when(articleMapper.toResults(any())).thenReturn(List.of(mock(ArticleResult.class)));

        articleService.afficher("MAJEUR");

        verify(articleRepository).findAll();
        verify(articleMapper).toResults(any());
        verifyNoInteractions(mineurRepository, mineurMapper);
    }

    /* =========================
     * create
     * ========================= */

    //@Test
    void create_should_save_article_when_type_is_MAJEUR() {
        ArticleInput input = new ArticleInput(Type.MAJEUR, "Abonement", new BigDecimal("5.00"));
        Article article = new Article();

        when(articleMapper.toEntity(input)).thenReturn(article);

        articleService.create(input);

        verify(articleMapper).toEntity(input);
        verify(articleRepository).save(article);
        verifyNoInteractions(mineurRepository);
    }

    @Test
    void create_should_save_mineur_when_type_is_not_MAJEUR() {
        ArticleInput input = new ArticleInput(Type.MINEUR, "Option", new BigDecimal("5.00"));
        Mineur mineur = new Mineur();

        when(mineurMapper.toEntity(input)).thenReturn(mineur);

        articleService.create(input);

        verify(mineurMapper).toEntity(input);
        verify(mineurRepository).save(mineur);
        verifyNoInteractions(articleRepository);
    }

    /* =========================
     * createLink
     * ========================= */

    @Test
    void createLink_should_create_link_when_article_and_mineur_exist() {
        Article article = Article.builder().id(1L).build();
        Mineur mineur = Mineur.builder().id(2L).build();

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(mineurRepository.findById(2L)).thenReturn(Optional.of(mineur));

        articleService.createLink(1L, 2L);

        verify(articleMineurRepository).save(any(ArticleMineur.class));
    }

    @Test
    void createLink_should_throw_exception_when_article_not_found() {
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.createLink(1L, 2L))
                .isInstanceOf(ArticleNotFoundException.class);

        verifyNoInteractions(mineurRepository, articleMineurRepository);
    }

    @Test
    void createLink_should_throw_exception_when_mineur_not_found() {
        Article article = Article.builder().id(1L).build();

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(mineurRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.createLink(1L, 2L))
                .isInstanceOf(MineurNotFoundException.class);

        verify(articleMineurRepository, never()).save(any());
    }
}
