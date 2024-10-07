import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const InquiryDetail = () => {
    const { inquiryId } = useParams(); // URL 파라미터에서 문의 ID 가져오기
    const [inquiry, setInquiry] = useState(null); // 문의 상세 정보를 저장할 상태
    const [answer, setAnswer] = useState(null); // 답변 정보를 저장할 상태
    const [isEditing, setIsEditing] = useState(false); // 문의 수정 모드 여부 상태
    const [isAnswerEditing, setIsAnswerEditing] = useState(false); // 답변 수정 모드 여부 상태
    const [title, setTitle] = useState(''); // 수정할 제목 상태
    const [content, setContent] = useState(''); // 수정할 내용 상태
    const [answerContent, setAnswerContent] = useState(''); // 수정할 답변 내용 상태
    const navigate = useNavigate(); // 페이지 이동을 위한 navigate 함수 생성
    const memberIdFromStorage = localStorage.getItem('memberId'); // LocalStorage에서 memberId 가져오기
    const memberId = memberIdFromStorage ? parseInt(memberIdFromStorage, 10) : null; // 숫자로 변환
    // const role = localStorage.getItem('role'); // LocalStorage에서 role 가져오기 (ADMIN 여부 확인)
    const role = 'ADMIN';

    useEffect(() => {
        const fetchInquiry = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/inquiries/${inquiryId}`);
                setInquiry(response.data);
                setTitle(response.data.inquiryTitle);
                setContent(response.data.inquiryContent);
            } catch (error) {
                console.error('Failed to fetch inquiry:', error);
            }
        };

        const fetchAnswer = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/answers/${inquiryId}`);
                setAnswer(response.data);
                setAnswerContent(response.data.answerContent);
            } catch (error) {
                setAnswer(null); // 답변이 없을 경우
            }
        };

        fetchInquiry(); // 문의 상세 정보 가져오기
        fetchAnswer(); // 답변 가져오기
    }, [inquiryId]);

    const handleDelete = async () => {
        const confirmDelete = window.confirm('정말로 삭제하시겠습니까?');
        if (confirmDelete) {
            try {
                await axios.delete(`http://localhost:8080/inquiries/${inquiryId}`);
                navigate('/inquiries'); // 삭제 후 문의 목록 페이지로 이동
            } catch (error) {
                console.error('Failed to delete inquiry:', error);
            }
        }
    };

    const handleAnswerDelete = async () => {
        const confirmDelete = window.confirm('정말로 답변을 삭제하시겠습니까?');
        if (confirmDelete) {
            try {
                await axios.delete(`http://localhost:8080/answers/${answer.answerId}`);
                setAnswer(null); // 답변 삭제 후 상태 초기화
            } catch (error) {
                console.error('Failed to delete answer:', error);
            }
        }
    };

    const handleEdit = () => {
        setIsEditing(true); // 문의 수정 모드로 변경
    };

    const handleAnswerEdit = () => {
        setIsAnswerEditing(true); // 답변 수정 모드로 변경
    };

    const handleUpdate = async () => {
        try {
            await axios.put(`http://localhost:8080/inquiries/${inquiryId}`, {
                inquiryTitle: title,
                inquiryContent: content,
                memberId: memberId
            });
            setInquiry({ ...inquiry, inquiryTitle: title, inquiryContent: content }); // 상태 업데이트
            setIsEditing(false); // 수정 모드 종료
        } catch (error) {
            console.error('Failed to update inquiry:', error);
        }
    };

    const handleAnswerUpdate = async () => {
        try {
            await axios.put(`http://localhost:8080/answers/${answer.answerId}`, {
                answerContent: answerContent,
            });
            setAnswer({ ...answer, answerContent: answerContent });
            setIsAnswerEditing(false); // 답변 수정 모드 종료
        } catch (error) {
            console.error('Failed to update answer:', error);
        }
    };

    const handleAnswerCreate = async () => {
        try {
            const response = await axios.post('http://localhost:8080/answers', {
                inquiryId: inquiryId,
                answerContent: answerContent,
            });
            setAnswer(response.data); // 답변 생성 후 상태 업데이트
        } catch (error) {
            console.error('Failed to create answer:', error);
        }
    };

    const handleBackToList = () => {
        navigate('/inquiries'); // 목록 페이지로 이동
    };

    if (!inquiry) return <p>로딩 중...</p>; // 문의가 로딩 중일 경우

    return (
        <div className="inquiry-detail">
            <h1>문의 상세 페이지</h1>

            {/* 문의 상세 정보 영역 */}
            <div className="inquiry-section">
                <div>
                    <strong>번호:</strong> {inquiry.inquiryId}
                </div>
                <div>
                    <strong>제목:</strong> {isEditing ? <input value={title} onChange={(e) => setTitle(e.target.value)} /> : inquiry.inquiryTitle}
                </div>
                <div>
                    <strong>작성자:</strong> {inquiry.memberNickname}
                </div>
                <div>
                    <strong>작성일:</strong> {new Date(inquiry.inquiryCreateDate).toLocaleDateString()}
                </div>
                <div>
                    <strong>수정일:</strong> {new Date(inquiry.inquiryUpdateDate).toLocaleDateString()}
                </div>
                <div>
                    <strong>상태:</strong> {inquiry.inquiryStatus}
                </div>
                <div>
                    <strong>내용:</strong> {isEditing ? <textarea value={content} onChange={(e) => setContent(e.target.value)} /> : inquiry.inquiryContent}
                </div>

                {/* 문의 수정/삭제 버튼 */}
                {inquiry.memberId === memberId && (
                    <div className="inquiry-actions">
                        {isEditing ? (
                            <button onClick={handleUpdate}>완료</button>
                        ) : (
                            <button onClick={handleEdit}>수정</button>
                        )}
                        <button onClick={handleDelete}>삭제</button>
                    </div>
                )}
            </div>

            {/* 답변 표시 영역 */}
            <div className="answer-section">
                <h2>문의 답변</h2>
                {answer ? (
                    <div>
                        <strong>답변 내용:</strong> {isAnswerEditing ? (
                        <textarea value={answerContent} onChange={(e) => setAnswerContent(e.target.value)} />
                    ) : (
                        answer.answerContent
                    )}
                        {role === 'ADMIN' && (
                            <div className="answer-actions">
                                {isAnswerEditing ? (
                                    <button onClick={handleAnswerUpdate}>답변 수정 완료</button>
                                ) : (
                                    <button onClick={handleAnswerEdit}>답변 수정</button>
                                )}
                                <button onClick={handleAnswerDelete}>답변 삭제</button>
                            </div>
                        )}
                    </div>
                ) : (
                    <p>아직 답변이 작성되지 않았습니다.</p>
                )}

                {role === 'ADMIN' && !answer && (
                    <div>
                        <textarea value={answerContent} onChange={(e) => setAnswerContent(e.target.value)} placeholder="답변을 입력하세요" />
                        <button onClick={handleAnswerCreate}>답변 작성</button>
                    </div>
                )}
            </div>

            <div className="actions">
                <button onClick={handleBackToList}>목록으로 돌아가기</button> {/* 목록으로 돌아가기 버튼 */}
            </div>
        </div>
    );
};

export default InquiryDetail;
