package canalplus.offres.offres.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import canalplus.offres.offres.domain.model.PromoArticle;

@Repository
public interface PromoArticleRepository extends JpaRepository<PromoArticle, Long> {

    List<PromoArticle> findByPromotionId(Long promotionId);

    List<PromoArticle> findByArticleId(Long articleId);

    @Query("""
        select pa
        from PromoArticle pa
        join fetch pa.article
        where pa.promotion.id = :promotionId
    """)
    List<PromoArticle> findAvecArticles(@Param("promotionId") Long promotionId);
}
