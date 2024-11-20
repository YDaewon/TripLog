package com.triplog.article.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.triplog.article.model.ArticleDto;

@Mapper
public interface ArticleMapper {
	// List Article
	List<ArticleDto> listAll(Map<String, String> map);
	
	// Detail Article
	ArticleDto getArticle(int articleNo);
	void UpdateHitLog(int articleNo, String userId);
	void UpdateHitCount(int articleNo);
	
	// Create Article
	void createArticle(ArticleDto articleDto);
	
	// Modify Article
	void modifyArticle(ArticleDto articleDto);
	
	// Delete Article
	void deleteArticle(int articleNo);
	
	// Link Plan
	void linkPlan(int articleNo, int planNo);

	void createStar(int articleNo, int userNo);

	void deleteStar(int articleNo, int userNo);
}
