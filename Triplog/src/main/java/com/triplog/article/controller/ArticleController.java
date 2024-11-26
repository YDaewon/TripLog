package com.triplog.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.triplog.article.model.ArticleDto;
import com.triplog.article.service.ArticleService;
import com.triplog.util.PageNavigation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Article Controller", description = "게시글 정보를 담당하는 컨트롤러입니다.")
@RestController
@RequestMapping("/article")
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		super();
		this.articleService = articleService;
	}
	
	@GetMapping("")
	@Operation(summary = "게시글 목록", description = "게시글 목록을 불러옴")
    public ResponseEntity<?> articleAll(
			@Parameter(description = "검색 조건") @RequestParam Map<String, String> map) {
        try {
            int pgno = Integer.parseInt(map.getOrDefault("pgno", "1")) - 1;
            int spp = Integer.parseInt(map.getOrDefault("spp", "10"));
            int totalCount = articleService.getTotalCount(map);
            System.out.println("totalCount: " + totalCount);
            map.put("curpgno", String.valueOf(pgno * spp));
            System.out.println(map.toString());
            Map<String, Object> result = new HashMap<>();
            List<ArticleDto> lists = articleService.listAll(map);
            if (lists != null) {
            	result.put("articles", lists); // 게시글 목록
                result.put("currentPage", pgno+1);
                result.put("totalPageCount", (totalCount / spp + 1));
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("리스트가 비어있어요");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로드 중 문제 발생");
        }
    }

	@GetMapping("{articleNo}")
	@Operation(summary = "게시글 정보", description = "articleNo에 해당하는 게시글 정보를 반환")
    public ResponseEntity<?> getinfo(
    		@Parameter(description = "게시글 번호", required = true)
    		@PathVariable("articleNo") int articleNo, HttpServletRequest request) {
		String userId = (String) request.getAttribute("userId");
        try {
        	articleService.UpdateHitLog(articleNo, userId);
        	articleService.UpdateHitCount(articleNo);
            ArticleDto articleDto = articleService.getArticle(articleNo);
            if (articleDto != null) {
                return ResponseEntity.ok(articleDto); // 로그인 성공 시 사용자 정보 반환
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("해당 개시글 정보가 없음");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로드 중 문제 발생");
        }
    }
	
	@GetMapping("/star/{articleNo}")
	@Operation(summary = "즐겨찾기 정보", description = "articleNo에 해당하는 게시글이 즐겨찾기 중인지를 반환")
    public ResponseEntity<?> isStar(
    		@Parameter(description = "게시글 번호", required = true)
    		@PathVariable("articleNo") int articleNo, HttpServletRequest request) {
		int userNo = (Integer) request.getAttribute("userNo");
        try {
            int star = articleService.isStar(articleNo, userNo);
            return ResponseEntity.ok(star); // 로그인 성공 시 사용자 정보 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로드 중 문제 발생");
        }
    }
	
	@PostMapping("")
	@Operation(summary = "게시글 등록", description = "게시글 등록을 담당")
	public ResponseEntity<String> create(
			 @Parameter(description = "게시글 정보", required = true)
			 @RequestBody ArticleDto ArticleDto, HttpServletRequest request) {
		//log.debug("Received ArticleDto: {}", articleDto); // 디버깅 로그
		int userNo = (Integer) request.getAttribute("userNo");
		//ArticleDto.setUserNo(userNo);
		System.out.println(ArticleDto);
        try {
    	    articleService.createArticle(ArticleDto);
    	    if(ArticleDto.getPlanNo() != 0) {
    	    	articleService.linkPlan(ArticleDto.getArticleNo(), ArticleDto.getPlanNo());
    	    }
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글 등록 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 등록 중 문제 발생");
        }
   }
	
	@PutMapping("")
    @Operation(summary = "게시글 수정", description = "게시글 정보를 수정하는 URL")
    public ResponseEntity<String> modify(@RequestBody ArticleDto articleDto, HttpServletRequest request) {
		int tokenNo = (Integer) request.getAttribute("userNo");
        try {
        	if(tokenNo == articleDto.getUserNo()) {
                articleService.modifyArticle(articleDto);
                return ResponseEntity.ok("게시글 수정 성공");
        	}
        	else {
        		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("작성자 외 수정 금지");
        	}
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 수정 중 문제 발생");
        }
    }
	
	@DeleteMapping("{articleNo}")
	@Operation(summary = "게시글 삭제", description = "articleNo에 해당하는 게시글 정보를 삭제")
    public ResponseEntity<String> delete(
    		@Parameter(description = "게시글 번호", required = true)
    		@PathVariable("articleNo") int articleNo,
            HttpServletRequest request) {
		int tokenNo = (Integer) request.getAttribute("userNo");
		int role = (Integer) request.getAttribute("role");
        try {
        	articleService.deleteStar(articleNo, tokenNo);
            articleService.deleteArticle(articleNo);
          return ResponseEntity.ok("게시글 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 문제 발생");
        }
    }
	
	@PostMapping("/star/{articleNo}")
	@Operation(summary = "즐겨찾기 추가", description = "articleNo에 해당하는 즐겨찾기 정보를 추가")
    public ResponseEntity<String> createStar(
    		@Parameter(description = "게시글 번호", required = true)
    		@PathVariable("articleNo") int articleNo, HttpServletRequest request) {
		int userNo = (Integer) request.getAttribute("userNo");
        try {
            articleService.createStar(articleNo, userNo);
            return ResponseEntity.ok("즐겨찾기 추가 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추가 중 문제 발생");
        }
    }
	
	@DeleteMapping("/star/{articleNo}")
	@Operation(summary = "즐겨찾기 삭제", description = "articleNo에 해당하는 즐겨찾기 정보를 삭제")
    public ResponseEntity<String> deleteStar(
    		@Parameter(description = "게시글 번호", required = true)
    		@PathVariable("articleNo") int articleNo, HttpServletRequest request) {
		int userNo = (Integer) request.getAttribute("userNo");
        try {
            articleService.deleteStar(articleNo, userNo);
            return ResponseEntity.ok("즐겨찾기 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 문제 발생");
        }
    }
	
	@PutMapping("/link/")
	@Operation(summary = "플랜 링크", description = "planNo에 해당하는 플랜으로 아티클과 연결")
    public ResponseEntity<String> linkPlan(
    		@Parameter(description = "플랜 번호", required = true)
    		@RequestParam Map<String, Integer> map, HttpServletRequest request) {
		int planNo = map.get("planNo");
		int articleNo = map.get("articleNo");
        try {
            articleService.linkPlan(articleNo, planNo);
            return ResponseEntity.ok("즐겨찾기 삭제 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 문제 발생");
        }
    }
	
}
