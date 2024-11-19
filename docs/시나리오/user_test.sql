use triplog;

-- SIGN
insert into users(user_id, user_pwd, user_name, nickname, email_id, email_domain, user_image)
	values("ssafy", "ssafy", "대원", "대복이", "qkqh9860", "naver.com", "");
    
insert into user(user_id, user_pwd, user_name, nickname, email_id, email_domain, user_image)
	values("ssafy2", "ssafy", "휘동", "휘복이", "rlagnlehd0417", "naver.com", "");

-- ID_CHECK
SELECT count(*) FROM users WHERE user_id = "ssafy1";

-- LOGIN
SELECT * FROM users WHERE user_id = "ssafy" AND user_pwd = "ssafy1";

-- DETAIL
SELECT user_no, user_id, user_pwd, user_name, nickname, email_id, email_domain, admin, user_image FROM users;

UPDATE users SET
    user_pwd = "1234",
    nickname = "대복대복",
    email_id = "ssafy",
    email_domain = "gmail.com",
    user_image = "img/img.png",
    admin = 1
where user_no = 1;

DELETE FROM users WHERE user_no = 1;