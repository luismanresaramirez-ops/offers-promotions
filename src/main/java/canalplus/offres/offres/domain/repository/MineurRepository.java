package canalplus.offres.offres.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import canalplus.offres.offres.domain.model.Mineur;

@Repository
public interface MineurRepository extends JpaRepository<Mineur, Long> {

}
