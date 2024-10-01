import React, { useState } from "react";
import styled from "styled-components";

const SignUp = () => {
    const [form, setForm] = useState({
        memberId: "",
        password: "",
        confirmPassword: "",
        nickname: "",
        phoneNumber: "",
        profileImage: "",
        introduction: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: value,
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        // 회원가입 로직 추가
        console.log(form);
    };

    const handleNaverClick = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/naver";
    };

    const handleGoogleClick = () => {
        window.location.href = "http://localhost:8080/login/oauth2/code/google";
    };

    return (
        <SignUpContainer>
            <Title>회원가입</Title>
            <Form onSubmit={handleSubmit}>
                <Label>회원 아이디</Label>
                <Input
                    type="text"
                    name="memberId"
                    value={form.memberId}
                    onChange={handleChange}
                    placeholder="아이디를 입력하세요"
                />

                <Label>비밀번호</Label>
                <Input
                    type="password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="비밀번호를 입력하세요"
                />

                <Label>비밀번호 확인</Label>
                <Input
                    type="password"
                    name="confirmPassword"
                    value={form.confirmPassword}
                    onChange={handleChange}
                    placeholder="비밀번호를 다시 입력하세요"
                />

                <SubmitButton type="submit">가입하기</SubmitButton>
            </Form>

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
