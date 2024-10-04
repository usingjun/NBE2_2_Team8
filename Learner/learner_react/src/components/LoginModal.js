import React, { useEffect, useState } from "react"; // useEffect 추가
import styled from "styled-components";

const LoginModal = ({ closeModal }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = async (event) => {
        event.preventDefault(); // 기본 동작 방지

        try {
            const response = await fetch("http://localhost:8080/join/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email: email,
                    password: password,
                }),
                credentials: "include", // 쿠키를 포함하려면 이 옵션을 추가해야 함
            });

            // 상태 코드 확인
            if (response.ok) {
                const { memberId } = await response.json();
                console.log("로그인 성공, memberId:", memberId);
                localStorage.setItem("memberId", memberId);
                alert("로그인에 성공하셨습니다.");
                closeModal();
                // 여기서 원하는 페이지로 리디렉션
                window.location.href = "http://localhost:3000/courses"; // 로그인 후 이동할 페이지
            } else {
                const errorMessage = await response.text();
                console.error(`오류 발생: ${response.status} - ${errorMessage}`);
                alert(`로그인 실패: ${errorMessage}`);
            }
        } catch (error) {
            console.error("로그인 요청 실패:", error);
            alert("로그인 요청 중 오류가 발생했습니다.");
        }
    };


    // 네이버 로그인 클릭 핸들러
    const handleNaverClick = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/naver"; // 네이버 로그인 페이지로 리디렉션
    };

    // 구글 로그인 클릭 핸들러
    const handleGoogleClick = () => {
        window.location.href = "http://localhost:8080/oauth2/authorization/google"; // 구글 로그인 페이지로 리디렉션
    };

    return (
        <ModalBackground>
            <ModalContainer>
                <CloseButton onClick={closeModal}>X</CloseButton>
                <Logo>Learner</Logo>
                <Form onSubmit={handleLogin}>
                    <Input
                        type="email"
                        placeholder="이메일"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <Input
                        type="password"
                        placeholder="비밀번호"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <LoginButton type="submit">로그인</LoginButton>
                </Form>
                <PasswordOptions>
                    <ButtonLink>아이디(이메일) 찾기</ButtonLink> |
                    <ButtonLink>비밀번호 찾기</ButtonLink> |
                    <ButtonLink>회원가입</ButtonLink>
                </PasswordOptions>

                {/* 소셜 로그인 버튼 */}
                <SocialLoginContainer>
                    <SocialLoginButton onClick={handleGoogleClick}>
                        <GoogleIcon>G</GoogleIcon> {/* 구글 아이콘 */}
                    </SocialLoginButton>
                    <SocialLoginButton onClick={handleNaverClick}>
                        <NaverIcon>N</NaverIcon> {/* 네이버 아이콘 */}
                    </SocialLoginButton>
                </SocialLoginContainer>
            </ModalContainer>
        </ModalBackground>
    );
};

export default LoginModal;

// 스타일 코드
const ModalBackground = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
`;

const ModalContainer = styled.div`
    background-color: white;
    padding: 2rem;
    border-radius: 10px;
    width: 400px;
    text-align: center;
    position: relative;
`;

const CloseButton = styled.button`
    position: absolute;
    top: 10px;
    right: 10px;
    background: none;
    border: none;
    font-size: 1.5rem;
    cursor: pointer;
`;

const Logo = styled.h1`
    font-size: 1.8rem;
    margin-bottom: 1rem;
`;

const Form = styled.form`
    display: flex;
    flex-direction: column;
`;

const Input = styled.input`
    padding: 0.75rem;
    margin-bottom: 1rem;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 1rem;
`;

const LoginButton = styled.button`
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

const PasswordOptions = styled.div`
    margin-top: 1rem;
    font-size: 0.875rem;
`;

const ButtonLink = styled.button`
    background: none;
    border: none;
    color: #555;
    font-size: 0.875rem;
    text-decoration: underline;
    cursor: pointer;
    &:hover {
        color: #28a745;
    }
`;

const SocialLoginContainer = styled.div`
    margin-top: 1rem;
    display: flex;
    justify-content: space-around;
`;

const SocialLoginButton = styled.div`
    width: 50px;
    height: 50px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    cursor: pointer;
`;

const GoogleIcon = styled(SocialLoginButton)`
    background-color: #ffffff;
    border: 1px solid #ddd;
`;

const NaverIcon = styled(SocialLoginButton)`
    background-color: #03c75a;
    color: white;
    font-weight: bold;
`;
