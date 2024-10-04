import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const CourseInquiryList = ({ courseId }) => {
    const navigate = useNavigate();
    const [inquiries, setInquiries] = useState([]);
    const [selectedInquiry, setSelectedInquiry] = useState(null);
    const [answers, setAnswers] = useState([]);
    const [newAnswer, setNewAnswer] = useState("");
    const [loading, setLoading] = useState(false);
    const [loadingDetail, setLoadingDetail] = useState(false);
    const [editAnswerId, setEditAnswerId] = useState(null);
    const [updatedAnswer, setUpdatedAnswer] = useState("");
    const [inquiryStatus, setInquiryStatus] = useState("PENDING");

    const role = localStorage.getItem("role"); // role을 가져옴

    useEffect(() => {
        setLoading(true);
        axios
            .get(`http://localhost:8080/course/${courseId}/course-inquiry`)
            .then((response) => {
                setInquiries(response.data);
                setLoading(false);
            })
            .catch((error) => {
                console.error("Error fetching the course inquiries:", error);
                setLoading(false);
            });
    }, [courseId]);

    const handleInquiryClick = (inquiryId) => {
        setLoadingDetail(true);
        axios
            .get(`http://localhost:8080/course/${courseId}/course-inquiry`)
            .then((response) => {
                const inquiry = response.data.find((item) => item.inquiryId === inquiryId);
                if (inquiry) {
                    setSelectedInquiry(inquiry);
                    setInquiryStatus(inquiry.inquiryStatus);
                } else {
                    console.error("해당 inquiryId의 문의를 찾을 수 없습니다.");
                }
                return axios.get(`http://localhost:8080/course/${courseId}/course-answer/${inquiryId}`);
            })
            .then((response) => {
                setAnswers(response.data);
                setLoadingDetail(false);
            })
            .catch((error) => {
                console.error("Error fetching inquiry details or answers:", error);
                setLoadingDetail(false);
            });
    };

    const handleAnswerSubmit = () => {
        if (!newAnswer.trim()) {
            console.error("No selected inquiry or empty answer");
            return;
        }
        axios
            .post(`http://localhost:8080/course/${courseId}/course-answer`, {
                inquiryId: selectedInquiry.inquiryId,
                answerContent: newAnswer,
            })
            .then(() => {
                return axios.get(
                    `http://localhost:8080/course/${courseId}/course-answer/${selectedInquiry.inquiryId}`
                );
            })
            .then((response) => {
                setAnswers(response.data);
                setNewAnswer("");
            })
            .catch((error) => {
                console.error("Error posting the answer:", error);
            });
    };

    const handleStatusChange = (status) => {
        axios
            .put(`http://localhost:8080/course/${courseId}/course-inquiry/${selectedInquiry.inquiryId}/status`, {
                inquiryStatus: status,
            })
            .then(() => {
                alert("문의 상태가 변경되었습니다.");
                setInquiryStatus(status);
            })
            .catch((error) => {
                console.error("Error updating inquiry status:", error);
            });
    };

    const handleDeleteInquiry = (inquiryId) => {
        if (window.confirm("정말로 이 문의를 삭제하시겠습니까?")) {
            axios
                .delete(`http://localhost:8080/course/${courseId}/course-inquiry/${inquiryId}`)
                .then(() => {
                    alert("문의가 성공적으로 삭제되었습니다.");
                    setInquiries(inquiries.filter((inquiry) => inquiry.inquiryId !== inquiryId));
                    setSelectedInquiry(null);
                })
                .catch((error) => {
                    console.error("Error deleting inquiry:", error);
                });
        }
    };

    const handleEditAnswerClick = (answer) => {
        setEditAnswerId(answer.answerId); // 수정할 답변 ID 설정
        setUpdatedAnswer(answer.answerContent); // 기존 답변 내용을 수정란에 미리 설정
    };

    const handleEditAnswerSubmit = (answerId) => {
        axios
            .put(`http://localhost:8080/course/${courseId}/course-answer/${answerId}`, {
                answerContent: updatedAnswer,
            })
            .then(() => {
                alert("답변이 성공적으로 수정되었습니다.");
                setEditAnswerId(null);
                setUpdatedAnswer("");
                return axios.get(
                    `http://localhost:8080/course/${courseId}/course-answer/${selectedInquiry.inquiryId}`
                );
            })
            .then((response) => {
                setAnswers(response.data);
            })
            .catch((error) => {
                console.error("Error updating answer:", error);
            });
    };

    const handleDeleteAnswer = (answerId) => {
        if (window.confirm("정말로 이 답변을 삭제하시겠습니까?")) {
            axios
                .delete(`http://localhost:8080/course/${courseId}/course-answer/${answerId}`)
                .then(() => {
                    alert("답변이 성공적으로 삭제되었습니다.");
                    setAnswers(answers.filter((answer) => answer.answerId !== answerId));
                })
                .catch((error) => {
                    console.error("Error deleting answer:", error);
                });
        }
    };

    const handleCancelEdit = () => {
        setEditAnswerId(null);
        setUpdatedAnswer("");
    };

    return (
        <>
            {loading ? (
                <p>로딩 중...</p>
            ) : (
                <>
                    <ButtonContainer>
                        {selectedInquiry ? (
                            <>
                                <BeforeButton onClick={() => setSelectedInquiry(null)}>이전 목록으로</BeforeButton>
                                <DeleteInquiryButton onClick={() => handleDeleteInquiry(selectedInquiry.inquiryId)}>
                                    문의 삭제
                                </DeleteInquiryButton>
                            </>
                        ) : (
                            <WriteButton onClick={() => navigate(`/courses/${courseId}/post`)}>글 작성하기</WriteButton>
                        )}
                    </ButtonContainer>

                    {selectedInquiry ? (
                        loadingDetail ? (
                            <p>문의 상세 정보를 로딩 중입니다...</p>
                        ) : (
                            <>
                                <InquiryDetail>
                                    <h3>{selectedInquiry.inquiryTitle}</h3>
                                    <p>
                                        <span style={{ whiteSpace: "pre-line" }}>{selectedInquiry.inquiryContent}</span>
                                    </p>
                                    <p style={{ fontSize: "0.9rem", color: "#555",  marginTop: "3rem"}}>
                                        작성자: {selectedInquiry.memberId} | 작성일:{" "}
                                        {new Date(selectedInquiry.createdDate).toLocaleDateString()}
                                    </p>
                                </InquiryDetail>

                                {role === "admin" && (
                                    <StatusSelect value={inquiryStatus} onChange={(e) => handleStatusChange(e.target.value)}>
                                        <option value="PENDING">PENDING</option>
                                        <option value="ANSWERED">ANSWERED</option>
                                        <option value="RESOLVED">RESOLVED</option>
                                    </StatusSelect>
                                )}

                                <AnswerList>
                                    <h4>답변 목록</h4>
                                    {answers.length > 0 ? (
                                        answers.map((answer) => (
                                            <AnswerItem key={answer.answerId}>
                                                {editAnswerId === answer.answerId ? (
                                                    <>
                                                        <textarea
                                                            style={{
                                                                width: "100%", // 테두리와 비슷한 크기로 조정
                                                                height: "150px", // 높이를 넉넉하게 조정
                                                                fontSize: "1rem"
                                                            }}
                                                            value={updatedAnswer}
                                                            onChange={(e) => setUpdatedAnswer(e.target.value)}
                                                            placeholder="답변 내용을 수정하세요"
                                                        />
                                                        <div>
                                                            <UpdateSubmitButton
                                                                onClick={() => handleEditAnswerSubmit(answer.answerId)}
                                                            >
                                                                수정 완료
                                                            </UpdateSubmitButton>
                                                            <CancelButton onClick={handleCancelEdit}>취소</CancelButton>
                                                        </div>
                                                    </>
                                                ) : (
                                                    <>
                                                        <p>
                                                            <strong>답변 내용:</strong> {answer.answerContent}
                                                        </p>
                                                        <p>
                                                            <strong>작성일:</strong>{" "}
                                                            {new Date(answer.answerCreateDate).toLocaleDateString()}
                                                        </p>
                                                    </>
                                                )}

                                                {role === "admin" && editAnswerId !== answer.answerId && (
                                                    <>
                                                        <AnswerButton onClick={() => handleEditAnswerClick(answer)}>
                                                            수정
                                                        </AnswerButton>
                                                        <AnswerButton
                                                            onClick={() => handleDeleteAnswer(answer.answerId)}
                                                            style={{ marginLeft: "10px" }}
                                                        >
                                                            삭제
                                                        </AnswerButton>
                                                    </>
                                                )}
                                            </AnswerItem>
                                        ))
                                    ) : (
                                        <p>답변이 없습니다.</p>
                                    )}
                                </AnswerList>

                                <AnswerForm>
                                    <textarea
                                        style={{
                                            width: "100%", // 테두리와 비슷한 크기로 조정
                                            height: "150px", // 높이를 넉넉하게 조정
                                            fontSize: "1rem"
                                        }}
                                        value={newAnswer}
                                        onChange={(e) => setNewAnswer(e.target.value)}
                                        placeholder="답변 내용을 입력하세요"
                                    />
                                    <SubmitButton onClick={handleAnswerSubmit}>답변 달기</SubmitButton>
                                </AnswerForm>
                            </>
                        )
                    ) : (
                        inquiries.length > 0 ? (
                            <InquiryList>
                                {inquiries.map((inquiry) => (
                                    <InquiryItem
                                        key={inquiry.inquiryId}
                                        onClick={() => handleInquiryClick(inquiry.inquiryId)}
                                    >
                                        <p>
                                            <strong>{inquiry.inquiryTitle}</strong>
                                        </p>
                                        <p style={{ fontSize: "0.9rem", color: "#555" }}>
                                            작성자 : {inquiry.memberId} 작성일 :
                                            {new Date(inquiry.createdDate).toLocaleDateString()}
                                        </p>
                                    </InquiryItem>
                                ))}
                            </InquiryList>
                        ) : (
                            <p>문의가 없습니다.</p>
                        )
                    )}
                </>
            )}
        </>
    );
};

export default CourseInquiryList;

// 스타일 컴포넌트들

const InquiryList = styled.div`
    margin-top: 1rem;
`;

const InquiryItem = styled.div`
    margin-bottom: 1rem;
    padding: 1rem;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    cursor: pointer;
`;

const InquiryDetail = styled.div`
    margin-top: 1rem;
    padding: 1.5rem;
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const AnswerList = styled.div`
    margin-top: 1.5rem;
`;

const AnswerItem = styled.div`
    margin-bottom: 1rem;
    padding: 1rem;
    background-color: #f9f9f9;
    border-radius: 5px;
    border: 1px solid #ddd;
`;

const AnswerForm = styled.div`
    margin-top: 2rem;
    display: flex;
    flex-direction: column;
    gap: 1rem;
    padding: 1rem;
    background-color: #f9f9f9;
    border-radius: 10px;
    max-width: 100%;
    width: 100%;
    box-sizing: border-box;
`;

const SubmitButton = styled.button`
    padding: 0.75rem 1.5rem;
    background-color: #3cb371;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1rem;
    &:hover {
        background-color: #2a9d63;
    }
`;

const UpdateSubmitButton = styled.button`
    padding: 0.25rem 0.75rem;
    margin-top: 0.5rem;
    margin-right: 0.5rem;
    background-color: #3cb371;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    &:hover {
        background-color: #2a9d63;
    }
`;

const CancelButton = styled.button`
    padding: 0.255rem 0.75rem;
    background-color: #ccc;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    &:hover {
        background-color: #bbb;
    }
`;

const ButtonContainer = styled.div`
    display: flex;
    justify-content: flex-end;
    margin-bottom: 1rem;
`;

const WriteButton = styled.button`
    padding: 0.75rem 1.5rem;
    background-color: #3cb371;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1rem;
    &:hover {
        background-color: #2a9d63;
    }
`;

const BeforeButton = styled.button`
    padding: 0.75rem 1.5rem;
    background-color: #3cb371;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1rem;
    &:hover {
        background-color: #2a9d63;
    }
`;

const DeleteInquiryButton = styled.button`
    padding: 0.75rem 1.5rem;
    margin-left: 0.5rem;
    background-color: #e74c3c;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1rem;
    &:hover {
        background-color: #c0392b;
    }
`;

const StatusSelect = styled.select`
    margin-top: 1rem;
    padding: 0.5rem;
    font-size: 1rem;
`;

const AnswerButton = styled.button`
    padding: 0.25rem 0.75rem;
    background-color: #3cb371;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    &:hover {
        background-color: #2a9d63;
    }
`;
