package canalplus.offres.offres.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import canalplus.offres.offres.controller.dto.request.ArticleInput;
import canalplus.offres.offres.controller.dto.request.Type;
import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.domain.model.Article;
import canalplus.offres.offres.domain.model.ArticleMineur;
import canalplus.offres.offres.domain.model.ArticleMineurId;
import canalplus.offres.offres.domain.model.Mineur;
import canalplus.offres.offres.domain.repository.ArticleMineurRepository;
import canalplus.offres.offres.domain.repository.ArticleRepository;
import canalplus.offres.offres.domain.repository.MineurRepository;
import canalplus.offres.offres.mapper.ArticleMapper;
import canalplus.offres.offres.mapper.MineurMapper;
import canalplus.offres.offres.service.exception.ArticleNotFoundException;
import canalplus.offres.offres.service.exception.MineurNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
	
	private final ArticleRepository articleRepository;
	private final MineurRepository mineurRepository;
	private final ArticleMineurRepository articleMineurRepository;
	private final ArticleMapper articleMapper;
	private final MineurMapper mineurMapper;
	private final static String MAJEUR = "MAJEUR";
	private final static String MINEUR = "MINEUR";
	
	public List<ArticleResult> afficher(String type) {
		if(type.equals(MINEUR)) {
			return mineurMapper.toResults(mineurRepository.findAll());
		} else {
			return articleMapper.toResults(articleRepository.findAll());
		}
	}
	
	
	@Transactional
	public void create(ArticleInput article) {
		if(article.type().equals(MAJEUR)) {
			articleRepository.save(articleMapper.toEntity(article));
		} else {
			mineurRepository.save(mineurMapper.toEntity(article));
		}
	}
	

	@Transactional
	public void createLink(Long idArticle, Long idMineur) {

	    Article article = articleRepository.findById(idArticle)
	            .orElseThrow(() -> new ArticleNotFoundException(idArticle));

	    Mineur mineur = mineurRepository.findById(idMineur)
	            .orElseThrow(() -> new MineurNotFoundException(idMineur));

	    ArticleMineurId id = new ArticleMineurId(
	            article.getId(),
	        mineur.getId()
	    );

	    ArticleMineur link = ArticleMineur.builder()
	            .id(id)
	            .article(article)
	            .mineur(mineur)
	            .build();

	    articleMineurRepository.save(link);
	}

}
