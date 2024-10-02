import React, { useState } from "react";
import styled from "styled-components";

const SignUp = () => {
    const [form, setForm] = useState({
        email: "", // 이메일 추가
        password: "",
        confirmPassword: "",
        nickname: "", // 닉네임 필드
        phoneNumber: "", // 전화번호 필드
        profileImage: "",
        introduction: "",
    });

    const [errorMessage, setErrorMessage] = useState(""); // 에러 메시지 상태 추가

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage(""); // 이전 에러 메시지 초기화
        try {
            const response = await fetch("http://localhost:8080/join/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(form), // form 데이터를 JSON 문자열로 변환
            });

            // 응답 상태가 2xx가 아닐 경우 처리
            if (!response.ok) {
                const errorData = await response.json(); // JSON 응답 파싱
                throw new Error(errorData.error); // 에러 메시지 추출
            }

            // 성공적으로 회원가입이 완료된 경우
            alert("회원가입에 성공하셨습니다."); // 알림창 표시
            window.location.href = "http://localhost:3000/courses"; // 리다이렉트

        } catch (error) {
            console.error("Error:", error);
            setErrorMessage(error.message); // 에러 메시지를 상태에 저장
        }
    };



    const handleNaverClick = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/naver";
    };

    const handleGoogleClick = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    return (
        <SignUpContainer>
            <Title>회원가입</Title>
            <Form onSubmit={handleSubmit}>
                <Label>이메일</Label>
                <Input
                    type="email" // 이메일 형식
                    name="email"
                    value={form.email}
                    onChange={handleChange}
                    placeholder="이메일을 입력하세요"
                    required // 필수 입력
                />

                <Label>비밀번호</Label>
                <Input
                    type="password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="비밀번호를 입력하세요"
                    required // 필수 입력
                />

                <Label>비밀번호 확인</Label>
                <Input
                    type="password"
                    name="confirmPassword"
                    value={form.confirmPassword}
                    onChange={handleChange}
                    placeholder="비밀번호를 다시 입력하세요"
                    required // 필수 입력
                />

                <Label>닉네임</Label>
                <Input
                    type="text"
                    name="nickname"
                    value={form.nickname}
                    onChange={handleChange}
                    placeholder="닉네임을 입력하세요"
                    required // 필수 입력
                />

                <Label>전화번호</Label>
                <Input
                    type="text"
                    name="phoneNumber"
                    value={form.phoneNumber}
                    onChange={handleChange}
                    placeholder="전화번호를 입력하세요"
                    required // 필수 입력
                />

                <SubmitButton type="submit">가입하기</SubmitButton>
            </Form>

            {errorMessage && <ErrorMessage>{errorMessage}</ErrorMessage>} {/* 에러 메시지 표시 */}

            <SimpleSignUp>
                <span>간편 회원가입</span>
                <SocialButtons>
                    <SocialButton onClick={handleNaverClick}>네이버</SocialButton>
                    <SocialButton onClick={handleGoogleClick}>구글</SocialButton>
                </SocialButtons>
            </SimpleSignUp>
        </SignUpContainer>
    );
};

export default SignUp;

const SignUpContainer = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 2rem;
`;

const Title = styled.h2`
    font-size: 2rem;
    margin-bottom: 2rem;
`;

const Form = styled.form`
    display: flex;
    flex-direction: column;
    width: 300px;
`;

const Label = styled.label`
    font-size: 1rem;
    margin-bottom: 0.5rem;
`;

const Input = styled.input`
    padding: 0.75rem;
    margin-bottom: 1rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1rem;
`;

const SubmitButton = styled.button`
    padding: 0.75rem;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 5px;
    font-size: 1.1rem;
    cursor: pointer;
    &:hover {
        background-color: #218838;
    }
`;

const SimpleSignUp = styled.div`
    margin-top: 2rem;
    text-align: center;
    span {
        margin-bottom: 1rem;
        display: block;
    }
`;

const SocialButtons = styled.div`
    display: flex;
    justify-content: center;
    gap: 1rem;
`;

const SocialButton = styled.button`
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    padding: 0.5rem 1rem;
    border-radius: 5px;
    cursor: pointer;
    &:hover {
        background-color: #f1f1f1;
    }
`;

const ErrorMessage = styled.div`
    color: red; /* 에러 메시지 색상 */
    margin-top: 1rem;
    font-size: 0.9rem;
`;
