package canalplus.offres.offres.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import canalplus.offres.offres.domain.model.AvantGratuit;

@Repository
public interface AvantGratuitRepository extends JpaRepository<AvantGratuit, Long> {

    List<AvantGratuit> findByPromotionId(Long promotionId);

    @Query("""
        select ag
        from AvantGratuit ag
        join fetch ag.codGratuit
        where ag.promotion.id = :promotionId
    """)
    List<AvantGratuit> findAvecCodGratuit(@Param("promotionId") Long promotionId);
}
