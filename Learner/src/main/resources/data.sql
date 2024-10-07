-- 초기 강의 데이터 2개 삽입.
INSERT IGNORE INTO course (course_id, course_attribute, course_name,course_description, course_level, course_price, member_nickname, sale, course_created_date)
                   VALUES (1, 'JAVA', 'JPA 강의', 'JPA 학습', 5, 63000, '김영한', false, NOW());

INSERT IGNORE INTO course (course_id, course_attribute, course_name,course_description, course_level, course_price, member_nickname, sale, course_created_date)
                   VALUES (2, 'JAVA', '스프링 입문', '코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술', 3, 35000, '김영한', false, NOW());

-- 초기 동영상 데이터 4개 삽입 --
INSERT IGNORE INTO videos (video_id , course_id, description, title, url, current_video_time, total_video_duration)
                   VALUES (1, 1, 'SQL 중심적인 개발의 문제점' , 'JPA', 'https://www.youtube.com/watch?v=_tMJPysViNU&list=PLumVmq_uRGHgP87Jf1-up391q_es9KiZ9&index=4' , 0.0, 1554);

INSERT IGNORE INTO videos (video_id , course_id, description, title, url, current_video_time, total_video_duration)
                   VALUES (2, 1, 'JPA 소개' , 'JPA', 'https://www.youtube.com/watch?v=nNNVb4l8tiA&list=PLumVmq_uRGHgP87Jf1-up391q_es9KiZ9&index=3' , 0.0, 1721);

INSERT IGNORE INTO videos (video_id , course_id, description, title, url, current_video_time, total_video_duration)
                   VALUES (3, 2, '스프링 입문 강의 소개' , '스프링', 'https://www.youtube.com/watch?v=qyGjLVQ0Hog&list=PLumVmq_uRGHgBrimIp2-7MCnoPUskVMnd&index=1' , 0.0, 301);

INSERT IGNORE INTO videos (video_id , course_id, description, title, url, current_video_time, total_video_duration)
                   VALUES (4, 2, '프로젝트생성' , '스프링', 'https://www.youtube.com/watch?v=NNClHeXzIBA&list=PLumVmq_uRGHgBrimIp2-7MCnoPUskVMnd&index=2' , 0.0, 990);

-- 초기 강의 리뷰 데이터 2개 삽입
INSERT IGNORE INTO review (review_id,review_name, review_detail ,rating,review_type,course_id,member_id,review_created_date,review_updated_date)
VALUES (1, '강추합니다!' ,'유익해요!', 5, 'COURSE', 1, 3, NOW(),NOW());

INSERT IGNORE INTO review (review_id,review_name, review_detail ,rating,review_type,course_id,member_id,review_created_date,review_updated_date)
VALUES (2, '그냥 돈값해요!' ,'감안하고 들으세요', 3, 'COURSE', 1, 3, NOW(),NOW());

INSERT IGNORE INTO review (review_id,review_name, review_detail ,rating,review_type,course_id,member_id,review_created_date,review_updated_date)
VALUES (3, '강추합니다!' ,'유익해요!', 5, 'INSTRUCTOR', 1, 3, NOW(),NOW());

INSERT IGNORE INTO review (review_id,review_name, review_detail ,rating,review_type,course_id,member_id,review_created_date,review_updated_date)
VALUES (4, '그냥 돈값해요!' ,'감안하고 들으세요', 3, 'INSTRUCTOR', 1, 3, NOW(),NOW());


-- 초기 강의 새소식 데이터 2개 삽입
INSERT IGNORE INTO news (news_id, like_count, view_count, course_id, news_date, news_description, news_name)
VALUES (1, 0, 0, 1, NOW(), '강의의 일부분이 바뀌었습니다!!', '강의 변경공지');

INSERT IGNORE INTO news (news_id, like_count, view_count, course_id, news_date, news_description, news_name)
VALUES (2, 0, 0, 1, NOW(), '강의 30% 할인 이벤트입니다.', '강의 할인공지');

-- 초기 수강 구매 목록 데이터 1개 삽입
INSERT IGNORE INTO member_course (member_course_id, member_id, course_id, purchase_date) VALUES (1, 2, 1, NOW());