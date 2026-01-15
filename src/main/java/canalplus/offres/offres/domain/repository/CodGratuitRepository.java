package canalplus.offres.offres.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import canalplus.offres.offres.domain.model.CodGratuit;

@Repository
public interface CodGratuitRepository extends JpaRepository<CodGratuit, Long> {

    Optional<CodGratuit> findByLgratuit(String lgratuit);

}
