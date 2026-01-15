package canalplus.offres.offres.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import canalplus.offres.offres.domain.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("""
        select p
        from Promotion p
        where p.dateDebut <= :date
          and p.dateFin >= :date
    """)
    List<Promotion> findPromotionsActives(@Param("date") LocalDate date);

}

