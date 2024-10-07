import React, { useEffect, useState } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";

const InstructorReview = () => {
    const { nickname } = useParams(); // URL에서 nickname 가져오기
    const [reviewList, setReviewList] = useState([]);
    const [averageRating, setAverageRating] = useState(0); // 평균 평점 상태 추가
    const [userId, setUserId] = useState(''); // 사용자의 memberId를 저장할 상태 추가
    const navigate = useNavigate(); // navigate 추가

    useEffect(() => {
        const token = localStorage.getItem("memberId");
        if (token) {
            fetch(`http://localhost:8080/members/${token}`, {
                credentials: 'include',
            })
                .then(res => res.json())
                .then(data => {
                    console.log("사용자 정보:", data);
                    setUserId(data.memberId); // 사용자 memberId 설정
                })
                .catch(err => console.error("사용자 정보 가져오기 실패:", err));
        }
    }, []);

    const fetchReviews = () => {
        fetch(`http://localhost:8080/members/instructor/${nickname}/reviews/list`, {
            credentials: 'include',
        })
            .then(res => res.json())
            .then(data => {
                console.log("Fetched instructor reviews:", data);
                setReviewList(data);
                calculateAverageRating(data); // 평균 평점 계산
            })
            .catch(err => console.error("리뷰 가져오기 실패:", err));
    };

    const calculateAverageRating = (reviews) => {
        if (reviews.length === 0) {
            setAverageRating(0); // 리뷰가 없을 경우 평균 평점 0으로 설정
            return;
        }
        const totalRating = reviews.reduce((sum, review) => sum + review.rating, 0);
        setAverageRating((totalRating / reviews.length).toFixed(1)); // 소수점 첫째 자리까지 표시
    };

    useEffect(() => {
        fetchReviews();
    }, [nickname]); // nickname에 따라 API 호출

    const handleDelete = (reviewId) => {
        const token = localStorage.getItem("memberId");
        if (window.confirm("정말 삭제하시겠습니까?")) {
            fetch(`http://localhost:8080/members/instructor/${nickname}/reviews/${reviewId}`, {
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

    return (
        <div className="review-container">
            <h2 className="review-header">강사 리뷰</h2>
            <h3 className="average-rating">평균 평점: {averageRating} / 5</h3> {/* 평균 평점 표시 */}
            <button
                className="add-review-button"
                onClick={() => navigate(`/members/instructor/${nickname}/reviews/create`)} // 리뷰 작성 페이지로 이동
            >
                리뷰 작성
            </button>
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
                                    <Link to={`/members/instructor/${nickname}/reviews/${review.reviewId}`} className="edit-button">수정</Link> {/* 수정 페이지로 이동 */}
                                </>
                            )}
                        </div>
                    </li>
                ))}
            </ul>
            <style jsx>{`
                .review-container {
                    max-width: 1000px;
                    margin: 0 auto;
                    padding: 30px;
                    background-color: #f8f9fa;
                    border-radius: 12px;
                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                    position: relative; /* 버튼 위치 조정을 위해 relative 추가 */
                }
                .review-header {
                    font-size: 28px;
                    color: #333;
                    margin-bottom: 10px;
                    border-bottom: 2px solid #28a745;
                    padding-bottom: 15px;
                }
                .average-rating {
                    font-size: 20px;
                    color: #28a745;
                    margin-bottom: 30px; /* 간격 조정 */
                }
                .add-review-button {
                    position: absolute; /* 버튼을 절대 위치로 설정 */
                    top: 30px; /* 상단 여백 */
                    right: 30px; /* 오른쪽 여백 */
                    padding: 10px 15px;
                    background-color: #28a745; /* 초록색 */
                    color: white;
                    border: none;
                    border-radius: 6px; /* 모서리 둥글게 */
                    cursor: pointer;
                    font-size: 16px;
                }
                .add-review-button:hover {
                    background-color: #218838; /* 버튼 호버 시 색상 변경 */
                }
                .review-list {
                    list-style-type: none;
                    padding: 0;
                }
                .review-item {
                    display: flex;
                    justify-content: space-between;
                    align-items: flex-start;
                    background-color: #fff;
                    border-radius: 10px;
                    padding: 20px;
                    margin-bottom: 20px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                    transition: transform 0.2s;
                }
                .review-item:hover {
                    transform: translateY(-3px);
                }
                .review-content {
                    flex-grow: 1;
                    margin-right: 20px;
                }
                .review-title {
                    font-weight: bold;
                    font-size: 20px;
                    margin: 0;
                }
                .review-detail {
                    margin: 8px 0;
                    font-size: 16px;
                    color: #555;
                }
                .review-rating {
                    font-size: 16px;
                    color: #28a745;
                }
                .button-group {
                    display: flex;
                    gap: 15px;
                }
                .delete-button, .edit-button {
                    padding: 10px 15px;
                    color: white;
                    border: none;
                    border-radius: 6px;
                    cursor: pointer;
                    font-size: 14px;
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

export default InstructorReview;
