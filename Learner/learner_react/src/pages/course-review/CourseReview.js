import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const CourseReview = ({ courseId }) => {
    const [reviewList, setReviewList] = useState([]);
    const [userNickname, setUserNickname] = useState('');
    const [userId, setUserId] = useState(''); // 사용자의 memberId를 저장할 상태 추가

    useEffect(() => {
        const token = localStorage.getItem("memberId");
        if (token) {
            fetch(`http://localhost:8080/members/${token}`, {
                credentials: 'include',
            })
                .then(res => res.json())
                .then(data => {
                    console.log("사용자 정보:", data);
                    setUserNickname(data.nickname);
                    setUserId(data.memberId); // 사용자 memberId 설정
                })
                .catch(err => console.error("사용자 정보 가져오기 실패:", err));
        }
    }, []);

    const fetchReviews = () => {
        fetch(`http://localhost:8080/course/${courseId}/reviews/list`, {
            credentials: 'include',
        })
            .then(res => res.json())
            .then(data => {
                console.log("Fetched reviews:", data);
                setReviewList(data);
            })
            .catch(err => console.error("리뷰 가져오기 실패:", err));
    };

    useEffect(() => {
        fetchReviews();
    }, [courseId]);

    const handleDelete = (reviewId) => {
        const token = localStorage.getItem("memberId");
        if (window.confirm("정말 삭제하시겠습니까?")) {
            fetch(`http://localhost:8080/course/${courseId}/reviews/${reviewId}`, {
                method: "DELETE",
                headers: {
                    'Content-Type': 'application/json',
                    'memberId': token  // memberId를 헤더에 추가
                },
                credentials: 'include',
            })
                .then(() => {
                    setReviewList(reviewList.filter(review => review.reviewId !== reviewId));
                    alert("리뷰가 삭제되었습니다.");
                })
                .catch(err => console.error("리뷰 삭제 실패:", err));
        }
    };

    const calculateAverageRating = () => {
        if (reviewList.length === 0) return 0;
        const totalRating = reviewList.reduce((acc, review) => acc + review.rating, 0);
        return (totalRating / reviewList.length).toFixed(1);
    };

    return (
        <div className="review-container">
            <h2 className="review-header">과정 리뷰</h2>
            <Link to={`/courses/${courseId}/reviews/create`} className="add-review-button">리뷰 작성</Link>

            <h3 className="average-rating">평균 평점: {calculateAverageRating()} / 5</h3>

            <ul className="review-list">
                {reviewList.map(review => (
                    <li key={review.reviewId} className="review-item">
                        <div className="review-content">
                            <h3 className="review-title">{review.reviewName}</h3>
                            <p className="review-detail">{review.reviewDetail}</p>
                            <span className="review-rating">평점: {review.rating} / 5</span>
                            <p className="review-author">작성자: {review.writerName}</p>
                        </div>
                        <div className="button-group">
                            {userId === review.writerId && ( // userId와 review의 writerId가 일치할 때만 버튼 표시
                                <>
                                    <button onClick={() => handleDelete(review.reviewId)} className="delete-button">삭제</button>
                                    <Link to={`/courses/${courseId}/reviews/${review.reviewId}/edit`} className="edit-button">수정</Link>
                                </>
                            )}
                        </div>
                    </li>
                ))}
            </ul>
            <style jsx>{`
                .review-container {
                    max-width: 1000px; /* 너비 증가 */
                    margin: 0 auto;
                    padding: 30px; /* 패딩 증가 */
                    background-color: #f8f9fa;
                    border-radius: 12px; /* 모서리 둥글게 */
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 그림자 강조 */
                    position: relative; /* 버튼 위치 조정을 위해 relative 추가 */
                }
                .review-header {
                    font-size: 28px; /* 헤더 크기 증가 */
                    color: #333;
                    margin-bottom: 30px; /* 아래 여백 증가 */
                    border-bottom: 2px solid #28a745;
                    padding-bottom: 15px; /* 패딩 증가 */
                }
                .add-review-button {
                    position: absolute; /* 버튼을 절대 위치로 설정 */
                    top: 30px; /* 상단 여백 */
                    right: 30px; /* 오른쪽 여백 */
                    padding: 12px 20px; /* 패딩 증가 */
                    background-color: #28a745; /* 초록색 */
                    color: white;
                    border: none;
                    border-radius: 6px; /* 모서리 둥글게 */
                    text-decoration: none;
                    font-size: 16px; /* 버튼 텍스트 크기 증가 */
                }
                .add-review-button:hover {
                    background-color: #218838; /* 버튼 호버 시 색상 변경 */
                }
                .average-rating {
                    font-size: 22px; /* 평균 평점 텍스트 크기 */
                    color: #28a745;
                    margin-top: 20px; /* 위쪽 여백 */
                }
                .review-list {
                    list-style-type: none;
                    padding: 0;
                }
                .review-item {
                    display: flex;
                    justify-content: space-between;
                    align-items: flex-start; /* 상단 정렬 */
                    background-color: #fff;
                    border-radius: 10px; /* 모서리 둥글게 */
                    padding: 20px; /* 패딩 증가 */
                    margin-bottom: 20px; /* 아래 여백 증가 */
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                    transition: transform 0.2s;
                }
                .review-item:hover {
                    transform: translateY(-3px);
                }
                .review-content {
                    flex-grow: 1;
                    margin-right: 20px; /* 버튼과의 간격 */
                }
                .review-title {
                    font-weight: bold;
                    font-size: 20px; /* 제목 크기 증가 */
                    margin: 0;
                }
                .review-detail {
                    margin: 8px 0; /* 위아래 여백 증가 */
                    font-size: 16px; /* 텍스트 크기 증가 */
                    color: #555;
                }
                .review-rating {
                    font-size: 16px; /* 평점 텍스트 크기 증가 */
                    color: #28a745;
                }
                .button-group {
                    display: flex;
                    gap: 15px; /* 버튼 간격 증가 */
                }
                .delete-button, .edit-button {
                    padding: 10px 15px; /* 패딩 증가 */
                    color: white;
                    border: none;
                    border-radius: 6px; /* 모서리 둥글게 */
                    cursor: pointer;
                    font-size: 14px; /* 버튼 텍스트 크기 증가 */
                }
                .delete-button {
                    background-color: #dc3545;
                }
                .edit-button {
                    background-color: #28a745;
                    text-decoration: none;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }
                .delete-button:hover, .edit-button:hover {
                    opacity: 0.8;
                }
            `}</style>
        </div>
    );
};

export default CourseReview;
