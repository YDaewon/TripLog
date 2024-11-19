-- 공지사항 추가
insert into notice (user_no, title, content) 
    values (2, "1","1"),
     (2, "2","2"),
     (2, "3","3"),
     (2, "4","4"),
     (2, "5","5");

-- 공지사항 조회
SELECT * FROM notice 
        ORDER BY created_at DESC 
        LIMIT 5;

-- 공지사항 상세
SELECT * FROM notice WHERE notice_no = 2;
        
-- 공지사항 수정
UPDATE notice set
   title = "2222",
   content = "2222"
where notice_no = 2;
        
-- 공지사항 삭제
UPDATE notice set
   deleted_at = current_timestamp
where notice_no = 2;