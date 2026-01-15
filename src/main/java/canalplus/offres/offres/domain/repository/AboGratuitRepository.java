package canalplus.offres.offres.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import canalplus.offres.offres.domain.model.AboGratuit;

@Repository
public interface AboGratuitRepository extends JpaRepository<AboGratuit, Long> {

    List<AboGratuit> findByNumContrat(Long numContrat);

    //Si actifsUniquement = false → le where devient juste numContrat = :numContrat, donc tous les mois gratuits sont retournés.
    @Query("""
            select ag
            from AboGratuit ag
            join fetch ag.codGratuit
            where ag.numContrat = :numContrat
              and (
                  :actifsUniquement = false or (
                      ag.dateDebut <= :date
                      and (ag.dateFin is null or ag.dateFin >= :date)
                  )
              )
        """)
        List<AboGratuit> findAbonnementsGratuitsActifs(
            @Param("numContrat") Long numContrat,
            @Param("date") LocalDate date,
            @Param("actifsUniquement") boolean actifsUniquement
        );

}
