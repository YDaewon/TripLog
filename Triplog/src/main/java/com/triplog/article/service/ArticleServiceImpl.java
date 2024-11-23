package com.triplog.article.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.triplog.article.model.ArticleDto;
import com.triplog.article.model.mapper.ArticleMapper;

@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleMapper articleMapper;
	
	public ArticleServiceImpl(ArticleMapper articleMapper) {
		super();
		this.articleMapper = articleMapper;
	}

    public int getTotalCount(Map<String, String> map) {
        return articleMapper.getTotalCount(map); // 전체 게시글 수 계산
    }
    
	@Override
	public List<ArticleDto> listAll(Map<String, String> map) {
		return articleMapper.listAll(map);
	}

	@Override
	public List<ArticleDto> mylist(Map<String, String> map) {
		return articleMapper.mylist(map);
	}
	
	@Override
	public ArticleDto getArticle(int articleNo) {
		// TODO Auto-generated method stub
		return articleMapper.getArticle(articleNo);
	}

	@Override
	public void UpdateHitLog(int articleNo, String userId) {
		articleMapper.UpdateHitLog(articleNo, userId);
	}

	@Override
	public void UpdateHitCount(int articleNo) {
		articleMapper.UpdateHitCount(articleNo);
		
	}

	@Override
	public void createArticle(ArticleDto articleDto) {
		articleMapper.createArticle(articleDto);
	}

	@Override
	public void modifyArticle(ArticleDto articleDto) {
		articleMapper.modifyArticle(articleDto);
	}

	@Override
	public void deleteArticle(int articleNo) {
		articleMapper.deleteArticle(articleNo);
	}

	@Override
	public void linkPlan(int articleNo, int planNo) {
		articleMapper.linkPlan(articleNo, planNo);
	}

	@Override
	public void createStar(int articleNo, int userNo) {
		articleMapper.createStar(articleNo, userNo);
	}
	
	@Override
	public void deleteStar(int articleNo, int userNo) {
		articleMapper.deleteStar(articleNo, userNo);
	}

	@Override
	public int isStar(int articleNo, int userNo) {
		return articleMapper.isStar(articleNo, userNo);
	}


}
