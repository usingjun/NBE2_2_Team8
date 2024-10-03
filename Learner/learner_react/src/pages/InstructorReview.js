import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const InstructorReviewList = ({ memberId }) => {
    const [reviewList, setReviewList] = useState([]);

    const fetchReviews = () => {
        fetch(`http://localhost:8080/members/${memberId}/reviews/list`, {
            credentials: 'include',
        })
            .then(res => res.json())
            .then(data => {
                console.log("Fetched instructor reviews:", data);
                setReviewList(data);
            })
            .catch(err => console.error("강사 리뷰 가져오기 실패:", err));
    };

    useEffect(() => {
        fetchReviews();
    }, [memberId]);

    const handleDelete = (reviewId) => {
        if (window.confirm("정말 삭제하시겠습니까?")) {
            fetch(`http://localhost:8080/members/${memberId}/reviews/delete?reviewId=${reviewId}`, {
                method: "DELETE",
                credentials: 'include',
            })
                .then(() => {
                    setReviewList(reviewList.filter(review => review.reviewId !== reviewId));
                    alert("강사 리뷰가 삭제되었습니다.");
                })
                .catch(err => console.error("강사 리뷰 삭제 실패:", err));
        }
    };

    return (
        <div className="review-container">
            <h2 className="review-header">강사 리뷰</h2>
            <Link to={`/members/${memberId}/reviews/create`} className="add-review-button">리뷰 추가</Link>
            <ul className="review-list">
                {reviewList.map(review => (
                    <li key={review.reviewId} className="review-item">
                        <div className="review-content">
                            <h3 className="review-title">{review.reviewName}</h3>
                            <p className="review-detail">{review.reviewDetail}</p>
                            <span className="review-rating">평점: {review.rating} / 5</span>
                        </div>
                        <div className="button-group">
                            <button onClick={() => handleDelete(review.reviewId)} className="delete-button">삭제</button>
                            <Link to={`/members/${memberId}/reviews/${review.reviewId}/edit`} className="edit-button">수정</Link>
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
                }
                .review-header {
                    font-size: 28px; 
                    color: #333;
                    margin-bottom: 30px; 
                    border-bottom: 2px solid #28a745;
                    padding-bottom: 15px; 
                }
                .add-review-button {
                    display: inline-block;
                    margin-bottom: 30px; 
                    padding: 12px 20px; 
                    background-color: #007bff;
                    color: white;
                    border: none;
                    border-radius: 6px; 
                    text-decoration: none;
                    font-size: 16px; 
                }
                .add-review-button:hover {
                    background-color: #0056b3;
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

export default InstructorReviewList;
