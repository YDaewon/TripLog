-- 게시글 추가
insert into articles (user_no, title, content) 
    values (2, "1","1"),
     (4, "2","2"),
     (2, "3","3"),
     (4, "4","4"),
     (2, "5","5");

truncate table articles;
-- 게시글 조회
SELECT 
    a.article_no AS articleNo,
    a.title AS articleTitle,
    u.nickname AS author,
    (SELECT count(*) from article_stars s where a.article_no = s.article_no) AS star,
    hit_count AS hitCount,
    a.created_at AS registTime
  FROM 
    articles a
  LEFT JOIN
    user u ON a.user_no = u.user_no
 -- WHERE a.deleted_at IS NULL
  ORDER BY a.created_at DESC;

-- 게시글 상세
INSERT INTO hit_article (user_no, article_no)
    values(4, 4),(2, 4),(2, 4),(2, 4),(4, 4),(4, 4),(4, 4); -- 조회 로그 기록
UPDATE articles set hit_count = hit_count + 1 where article_no = 4;
SELECT * from hit_article;
-- ------------------------------------------------
SELECT * from article where article_no = 4;
        
-- 공지사항 수정
UPDATE articles set
   title = "2222",
   content = "2222"
where article_no = 2;
        
-- 공지사항 삭제
UPDATE articles set
   deleted_at = current_timestamp
where article_no = 2;

-- 즐겨찾기 추가
INSERT INTO article_stars (article_no, user_no)
	VALUES (4, 2);
INSERT INTO article_stars (article_no, user_no)
	VALUES (2, 2);
    
-- 즐겨찾기 삭제
DELETE FROM article_stars WHERE article_no = 4 AND user_no = 2;