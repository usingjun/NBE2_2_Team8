-- 초기 강의 데이터 1개 삽입.
INSERT IGNORE INTO course (course_id, course_attribute, course_name,course_description, course_level, course_price, member_nickname, sale, course_created_date)
                   VALUES (1, 'JAVA', 'JPA 강의', 'JPA 학습', 5, 63000, '김영한', false, NOW());

-- 초기 동영상 데이터 2개 삽입 --
INSERT IGNORE INTO videos (video_id , course_id, description, title, url, current_video_time, total_video_duration)
                   VALUES (1, 1, 'SQL 중심적인 개발의 문제점' , 'JPA', 'https://www.youtube.com/watch?v=_tMJPysViNU&list=PLumVmq_uRGHgP87Jf1-up391q_es9KiZ9&index=4' , 0.0, 1554);

INSERT IGNORE INTO videos (video_id , course_id, description, title, url, current_video_time, total_video_duration)
                   VALUES (2, 1, 'JPA 소개' , 'JPA', 'https://www.youtube.com/watch?v=nNNVb4l8tiA&list=PLumVmq_uRGHgP87Jf1-up391q_es9KiZ9&index=3' , 0.0, 1721);


-- 초기 강의 새소식 데이터 2개 삽입
INSERT IGNORE INTO news (news_id, like_count, view_count, course_id, news_date, news_description, news_name)
VALUES (1, 0, 0, 1, NOW(), '강의의 일부분이 바뀌었습니다!!', '강의 변경공지');

INSERT IGNORE INTO news (news_id, like_count, view_count, course_id, news_date, news_description, news_name)
VALUES (2, 0, 0, 1, NOW(), '강의 30% 할인 이벤트입니다.', '강의 할인공지');