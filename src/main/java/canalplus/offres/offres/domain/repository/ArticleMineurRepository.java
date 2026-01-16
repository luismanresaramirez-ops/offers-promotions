package canalplus.offres.offres.domain.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import canalplus.offres.offres.domain.model.Article;
import canalplus.offres.offres.domain.model.ArticleMineur;
import canalplus.offres.offres.domain.model.ArticleMineurId;

public interface ArticleMineurRepository extends JpaRepository<ArticleMineur, ArticleMineurId> {


}
