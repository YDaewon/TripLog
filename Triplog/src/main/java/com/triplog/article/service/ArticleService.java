package com.triplog.article.service;

import java.util.List;
import java.util.Map;

import com.triplog.article.model.ArticleDto;

public interface ArticleService {
	// List Article
	List<ArticleDto> listAll(Map<String, String> map);
    public int getTotalCount(Map<String, String> map);
	
	// Detail Article
	ArticleDto getArticle(int articleNo);
	void UpdateHitLog(int attractionNo, String userId);
	void UpdateHitCount(int attractionNo);
	
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

	List<ArticleDto> mylist(Map<String, String> map);
	int isStar(int articleNo, int userNo);

}
