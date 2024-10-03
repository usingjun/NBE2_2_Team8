import React, { useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import styled from "styled-components"; // styled-components를 사용하여 스타일 추가

const ReviewCreate = () => {
    const { courseId } = useParams();
    const navigate = useNavigate();

    const [reviewName, setReviewName] = useState("");
    const [reviewDetail, setReviewDetail] = useState("");
    const [rating, setRating] = useState(1);

    const handleSubmit = (e) => {
        e.preventDefault();

        const reviewData = { reviewName, reviewDetail, rating };

        fetch(`http://localhost:8080/course/${courseId}/reviews`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(reviewData),
            credentials: 'include',
        })
            .then((res) => {
                if (res.ok) {
                    alert("리뷰가 성공적으로 등록되었습니다.");
                    navigate(`/courses/${courseId}`); // 과정 상세 페이지로 이동
                } else {
                    throw new Error("리뷰 등록 실패");
                }
            })
            .catch((err) => console.error(err));
    };

    return (
        <FormContainer>
            <h2>리뷰 작성</h2>
            <Form onSubmit={handleSubmit}>
                <InputContainer>
                    <Label>리뷰 제목:</Label>
                    <Input
                        type="text"
                        value={reviewName}
                        onChange={(e) => setReviewName(e.target.value)}
                        required
                    />
                </InputContainer>
                <InputContainer>
                    <Label>리뷰 내용:</Label>
                    <TextArea
                        value={reviewDetail}
                        onChange={(e) => setReviewDetail(e.target.value)}
                        required
                    />
                </InputContainer>
                <InputContainer>
                    <Label>평점:</Label>
                    <Select value={rating} onChange={(e) => setRating(e.target.value)} required>
                        {[1, 2, 3, 4, 5].map((rate) => (
                            <option key={rate} value={rate}>{rate}</option>
                        ))}
                    </Select>
                </InputContainer>
                <ButtonContainer>
                    <SubmitButton type="submit">등록</SubmitButton>
                    <CancelButton type="button" onClick={() => navigate(`/courses/${courseId}`)}>취소</CancelButton>
                </ButtonContainer>
            </Form>
        </FormContainer>
    );
};

export default ReviewCreate;

// Styled Components
const FormContainer = styled.div`
    max-width: 800px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const Form = styled.form`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;

const InputContainer = styled.div`
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
`;

const Label = styled.label`
    font-size: 1rem;
    font-weight: bold;
`;

const Input = styled.input`
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1rem;
    &:focus {
        border-color: #3cb371; /* 포커스 시 색상 변경 */
        outline: none; /* 포커스 아웃라인 제거 */
    }
`;

const TextArea = styled.textarea`
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1rem;
    height: 150px; /* 높이를 적절히 조정 */
    resize: none; /* 크기 조절 비활성화 */
    &:focus {
        border-color: #3cb371; /* 포커스 시 색상 변경 */
        outline: none; /* 포커스 아웃라인 제거 */
    }
`;

const Select = styled.select`
    padding: 0.75rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1rem;
`;

const ButtonContainer = styled.div`
    display: flex;
    justify-content: flex-end;
    gap: 1rem;
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

const CancelButton = styled.button`
    padding: 0.75rem 1.5rem;
    background-color: #ccc;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 1rem;
    &:hover {
        background-color: #bbb;
    }
`;
