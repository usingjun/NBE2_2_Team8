import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const CourseNewsList = ({ courseId }) => {
    const [newsList, setNewsList] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const newsPerPage = 5; // 한 페이지당 보여줄 뉴스 개수

    const fetchNews = (page) => {
        fetch(`http://localhost:8080/course/${courseId}/news?page=${page}&size=${newsPerPage}`, {
            credentials: 'include',
        })
            .then(res => res.json())
            .then(data => {
                console.log("Fetched news:", data);
                setNewsList(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(err => console.error("새소식 가져오기 실패:", err));
    };

    useEffect(() => {
        fetchNews(currentPage);
    }, [courseId, currentPage]);

    const handlePrevPage = () => {
        if (currentPage > 0) {
            setCurrentPage(currentPage - 1);
        }
    };

    const handleNextPage = () => {
        if (currentPage < totalPages - 1) {
            setCurrentPage(currentPage + 1);
        }
    };

    return (
        <div className="news-container">
            <h2 className="news-header">과정 새소식</h2>
            <ul className="news-list">
                {newsList.map(news => (
                    <li key={news.newsId} className="news-item">
                        <Link to={`/courses/${courseId}/news/${news.newsId}`} className="news-link">
                            <span className="news-title">{news.newsName}</span>
                            <span className="news-arrow">→</span>
                        </Link>
                    </li>
                ))}
            </ul>
            <div className="pagination">
                <button onClick={handlePrevPage} disabled={currentPage === 0}>이전</button>
                <span>{currentPage + 1} / {totalPages}</span>
                <button onClick={handleNextPage} disabled={currentPage === totalPages - 1}>다음</button>
            </div>
            <style jsx>{`
                .news-container {
                    max-width: 800px;
                    margin: 0 auto;
                    padding: 20px;
                    background-color: #f8f9fa;
                    border-radius: 8px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                }
                .news-header {
                    font-size: 24px;
                    color: #333;
                    margin-bottom: 20px;
                    border-bottom: 2px solid #007bff;
                    padding-bottom: 10px;
                }
                .news-list {
                    list-style-type: none;
                    padding: 0;
                }
                .news-item {
                    margin-bottom: 10px;
                    transition: transform 0.2s;
                }
                .news-item:hover {
                    transform: translateX(5px);
                }
                .news-link {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    padding: 15px;
                    background-color: white;
                    border-radius: 4px;
                    color: #333;
                    text-decoration: none;
                    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
                }
                .news-title {
                    font-weight: 500;
                }
                .news-arrow {
                    color: #007bff;
                }
                .pagination {
                    display: flex;
                    justify-content: space-between;
                    margin-top: 20px;
                }
                .pagination button {
                    padding: 10px 15px;
                    background-color: #007bff;
                    color: white;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                }
                .pagination button:disabled {
                    background-color: #ccc;
                    cursor: not-allowed;
                }
            `}</style>
        </div>
    );
};

export default CourseNewsList;
