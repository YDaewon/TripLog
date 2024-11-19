SELECT * FROM attractions
WHERE
si_gun_gu_code = 21;

SELECT * FROM attractions
WHERE
title LIKE CONCAT('%', "길", '%');

SELECT * FROM attractions
WHERE
area_code = 35;

SELECT * FROM attractions
WHERE
content_type_id = 25;

SELECT content_type_id, count(*) FROM attractions
group by content_type_id;

