package canalplus.offres.offres.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import canalplus.offres.offres.domain.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByDesignation(String designation);

    // join fetch évite le probleme N+1 pour chaque entites N requettes lancées
    @Query("""
        select distinct a
        from Article a
        join fetch a.promoArticles pa
        join fetch pa.promotion p
        where p.dateDebut <= :date
          and p.dateFin >= :date
    """)
    List<Article> findArticlesEnPromotion(@Param("date") LocalDate date);
}
