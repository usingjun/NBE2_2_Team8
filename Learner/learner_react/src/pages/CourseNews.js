import React, { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import styled from "styled-components";

export default function CourseNews() {
    const { courseId, newsId } = useParams();
    const [news, setNews] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8080/course/${courseId}/news/${newsId}`, {
            credentials: 'include',
        })
            .then(res => res.json())
            .then(data => {
                console.log("Fetched news:", data);
                setNews(data); // 데이터가 이미 객체 형태이므로 그대로 설정
            })
            .catch(err => console.error("새소식 가져오기 실패:", err));
    }, [courseId, newsId]);

    if (!news) {
        return <div>로딩 중...</div>;
    }

    return (
        <NewsContainer>
            <NewsHeader>{news.newsName}</NewsHeader>
            <NewsMetaInfo>
                <span>작성일: {new Date(news.newsDate).toLocaleDateString()}</span>
                <span>조회수: {news.viewCount}</span>
                <span>좋아요: {news.likeCount}</span>
            </NewsMetaInfo>
            <NewsContent>{news.newsDescription}</NewsContent>
            <NewsFooter>
                마지막 수정일: {new Date(news.lastModifiedDate).toLocaleString()}
            </NewsFooter>
        </NewsContainer>
    );
}

const NewsContainer = styled.div`
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const NewsHeader = styled.h2`
    font-size: 24px;
    color: #333;
    margin-bottom: 10px;
`;

const NewsMetaInfo = styled.div`
    display: flex;
    justify-content: space-between;
    color: #666;
    font-size: 14px;
    margin-bottom: 20px;
`;

const NewsContent = styled.p`
    font-size: 16px;
    line-height: 1.6;
    color: #333;
    margin-bottom: 20px;
`;

const NewsFooter = styled.div`
    font-size: 12px;
    color: #999;
    text-align: right;
`;