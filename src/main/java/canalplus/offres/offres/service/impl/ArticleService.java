package canalplus.offres.offres.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import canalplus.offres.offres.controller.dto.response.ArticleResult;
import canalplus.offres.offres.domain.repository.AboGratuitRepository;
import canalplus.offres.offres.domain.repository.ArticleRepository;
import canalplus.offres.offres.mapper.ArticleMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
	
	private final ArticleRepository articleRepository;
	private final ArticleMapper articleMapper;
	
	public List<ArticleResult> afficher() {
		return articleMapper.fromArticle(articleRepository.findAll());
	}
}
