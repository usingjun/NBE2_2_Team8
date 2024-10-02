import React, { useState } from "react";
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
                // 로그인 성공 처리
                closeModal(); // 로그인 성공 시 모달 닫기
                console.log("로그인 성공");
                alert("로그인에 성공하셨습니다."); // 알림창 표시

                // 쿠키가 있는지 확인
                const cookies = document.cookie;
                console.log("받은 쿠키:", cookies);
            } else {
                // 오류 상태 코드와 메시지 처리
                const errorMessage = await response.text(); // 서버가 반환하는 오류 메시지
                console.error(`오류 발생: ${response.status} - ${errorMessage}`);

                // 서버에서 반환한 오류 메시지를 사용자에게 알림
                alert(`로그인 실패: ${errorMessage}`); // 오류 메시지를 사용자에게 알림
            }
        } catch (error) {
            console.error("로그인 요청 실패:", error);
            alert("로그인 요청 중 오류가 발생했습니다."); // 오류 알림
        }
    };


    return (
        <ModalBackground>
            <ModalContainer>
                <CloseButton onClick={closeModal}>X</CloseButton>
                <Logo>Learner</Logo>
                <Form onSubmit={handleLogin}> {/* onSubmit 추가 */}
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
                    <LoginButton type="submit">로그인</LoginButton> {/* type="submit" 설정 */}
                </Form>
                <PasswordOptions>
                    <ButtonLink>아이디(이메일) 찾기</ButtonLink> |
                    <ButtonLink>비밀번호 찾기</ButtonLink> |
                    <ButtonLink>회원가입</ButtonLink>
                </PasswordOptions>
            </ModalContainer>
        </ModalBackground>
    );
};

export default LoginModal;

// 스타일 코드 동일


// 스타일 코드 동일
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
  a {
    text-decoration: none;
    color: #555;
    &:hover {
      color: #28a745;
    }
  }
`;

const SimpleLogin = styled.div`
  margin-top: 2rem;
  text-align: center;
  p {
    margin-bottom: 0.5rem;
    font-weight: bold;
  }
`;

const SocialLoginButton = styled.div`
  display: flex;
  justify-content: center;
  gap: 1rem;
`;

const KakaoIcon = styled.div`
  width: 50px;
  height: 50px;
  background-color: #fddc3f;
  border-radius: 50%;
`;

const GoogleIcon = styled.div`
  width: 50px;
  height: 50px;
  background-color: #ffffff;
  border: 1px solid #ddd;
  border-radius: 50%;
`;

const GithubIcon = styled.div`
  width: 50px;
  height: 50px;
  background-color: #000000;
  border-radius: 50%;
  color: white;
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
