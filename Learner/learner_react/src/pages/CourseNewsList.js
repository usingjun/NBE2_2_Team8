import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";

const CourseNewsList = ({ courseId }) => {
    const [newsList, setNewsList] = useState([]);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [userRole, setUserRole] = useState(null);
    const newsPerPage = 5;
    const navigate = useNavigate();

    useEffect(() => {
        // 컴포넌트 마운트 시 사용자 권한 확인
        checkUserRole();
    }, []);

    const checkUserRole = () => {
        try {
            // 쿠키에서 JWT 토큰 가져오기 (쿠키 이름은 실제 사용하는 이름으로 변경 필요)
            const token = document.cookie
                .split('; ')
                .find(row => row.startsWith('Authorization='))
                ?.split('=')[1];

            console.log("토큰:", token);
            if (token) {
                const decodedToken = jwtDecode(token); // jwtDecode 사용
                setUserRole(decodedToken.role); // JWT 토큰에서 role 정보 추출
                console.log("사용자 권한:", decodedToken.role);
            }
        } catch (error) {
            console.error("토큰 확인 중 오류 발생:", error);
        }
    };

    const canCreateNews = () => {
        return userRole === 'Role_INSTRUCTOR' || userRole === 'Role_ADMIN';
    };


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

    const handleCreateNews = () => {
        if (canCreateNews()) {
            navigate(`/courses/${courseId}/news/create`);
        } else {
            alert('새소식 등록은 강사 또는 관리자만 가능합니다.');
        }
    };

    return (
        <div style={styles.newsContainer}>
            <h2 style={styles.newsHeader}>과정 새소식</h2>
            {canCreateNews() && (
                <button onClick={handleCreateNews} style={styles.createNewsButton}>
                    새소식 등록하기
                </button>
            )}
            <ul style={styles.newsList}>
                {newsList.map(news => (
                    <li key={news.newsId} style={styles.newsItem}>
                        <Link
                            to={`/courses/${courseId}/news/${news.newsId}`}
                            style={styles.newsLink}
                        >
                            <span style={styles.newsTitle}>{news.newsName}</span>
                            <span style={styles.newsArrow}>→</span>
                        </Link>
                    </li>
                ))}
            </ul>
            <div style={styles.pagination}>
                <button
                    onClick={handlePrevPage}
                    disabled={currentPage === 0}
                    style={currentPage === 0 ? {...styles.paginationButton, ...styles.disabledButton} : styles.paginationButton}
                >
                    이전
                </button>
                <span>{currentPage + 1} / {totalPages}</span>
                <button
                    onClick={handleNextPage}
                    disabled={currentPage === totalPages - 1}
                    style={currentPage === totalPages - 1 ? {...styles.paginationButton, ...styles.disabledButton} : styles.paginationButton}
                >
                    다음
                </button>
            </div>
        </div>
    );
};

const styles = {
    newsContainer: {
        maxWidth: '800px',
        margin: '0 auto',
        padding: '20px',
        backgroundColor: '#f8f9fa',
        borderRadius: '8px',
        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    },
    newsHeader: {
        fontSize: '24px',
        color: '#333',
        marginBottom: '20px',
        borderBottom: '2px solid #007bff',
        paddingBottom: '10px',
    },
    createNewsButton: {
        padding: '10px 15px',
        backgroundColor: '#007bff',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        marginBottom: '20px',
    },
    newsList: {
        listStyleType: 'none',
        padding: 0,
    },
    newsItem: {
        marginBottom: '10px',
        transition: 'transform 0.2s',
    },
    newsLink: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '15px',
        backgroundColor: 'white',
        borderRadius: '4px',
        color: '#333',
        textDecoration: 'none',
        boxShadow: '0 1px 3px rgba(0, 0, 0, 0.1)',
    },
    newsTitle: {
        fontWeight: 500,
    },
    newsArrow: {
        color: '#007bff',
    },
    pagination: {
        display: 'flex',
        justifyContent: 'space-between',
        marginTop: '20px',
    },
    paginationButton: {
        padding: '10px 15px',
        backgroundColor: '#007bff',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
    },
    disabledButton: {
        backgroundColor: '#ccc',
        cursor: 'not-allowed',
    },
};

export default CourseNewsList;