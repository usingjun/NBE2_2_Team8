import React, { useEffect, useState } from "react";
import styled from "styled-components";

const MyPage = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [selectedFile, setSelectedFile] = useState(null);
    const [isHover, setIsHover] = useState(false); // hover 상태 추가

    useEffect(() => {
        const memberId = localStorage.getItem("memberId"); // 로컬 저장소에서 memberId 가져오기
        const fetchUserInfo = async () => {
            try {
                const response = await fetch(`http://localhost:8080/members/${memberId}`, {
                    credentials: "include", // 쿠키 포함
                });
                if (response.ok) {
                    const data = await response.json();
                    setUserInfo(data);
                } else {
                    console.error("사용자 정보 로드 실패:", response.status);
                }
            } catch (error) {
                console.error("API 호출 중 오류 발생:", error);
            }
        };

        fetchUserInfo();
    }, []);

    const handleLogout = () => {
        document.cookie = "Authorization=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        localStorage.removeItem("memberId");
        window.location.href = "/"; // 로그아웃 후 홈으로 리디렉션
    };

    const handleFileChange = async (event) => {
        const file = event.target.files[0];
        if (file) {
            setSelectedFile(file);
            // 이미지 업로드 요청
            const memberId = localStorage.getItem("memberId");
            const formData = new FormData();
            formData.append("file", file);
            try {
                const response = await fetch(`http://localhost:8080/${memberId}/image`, {
                    method: "POST",
                    body: formData,
                    credentials: "include", // 쿠키 포함
                });
                if (response.ok) {
                    const data = await response.json();
                    setUserInfo((prevUserInfo) => ({
                        ...prevUserInfo,
                        profileImage: data.profileImage, // 업로드된 이미지로 사용자 정보 업데이트
                    }));
                } else {
                    console.error("이미지 업로드 실패:", response.status);
                }
            } catch (error) {
                console.error("이미지 업로드 중 오류 발생:", error);
            }
        }
    };

    const handleUploadClick = () => {
        const fileInput = document.getElementById("fileInput");
        fileInput.click(); // 파일 업로드 창 열기
    };

    if (!userInfo) {
        return <LoadingMessage>로딩 중...</LoadingMessage>;
    }

    // 프로필 이미지가 null일 경우 기본 이미지로 설정
    const profileImageSrc = userInfo.profileImage
        ? `data:image/jpeg;base64,${userInfo.profileImage}` // base64 형식으로 변환
        : "http://localhost:8080/images/default_profile.jpg"; // 기본 이미지 경로

    return (
        <Container>
            <Title>마이페이지</Title>
            <ProfileSection
                onMouseEnter={() => setIsHover(true)} // 마우스 엔터
                onMouseLeave={() => setIsHover(false)} // 마우스 리브
            >
                <ProfilePicture src={profileImageSrc} alt="Profile" />
                {isHover && ( // hover 상태일 때만 버튼 보이기
                    <UploadButton onClick={handleUploadClick}>
                        +
                    </UploadButton>
                )}
                <input
                    type="file"
                    id="fileInput"
                    style={{ display: "none" }}
                    accept="image/*"
                    onChange={handleFileChange}
                />
                <UserInfo>
                    <InfoItem><strong>닉네임:</strong> {userInfo.nickname}</InfoItem>
                    <InfoItem><strong>이메일:</strong> {userInfo.email}</InfoItem>
                    <InfoItem><strong>전화번호:</strong> {userInfo.phone}</InfoItem>
                </UserInfo>
            </ProfileSection>
            <AboutSection>
                <AboutTitle>자기소개</AboutTitle>
                <AboutContent>{userInfo.introduction || "자기소개가 없습니다."}</AboutContent>
            </AboutSection>
            <ButtonContainer>
                <StyledButton onClick={() => window.location.href = "/edit-profile"}>회원정보 수정</StyledButton>
                <StyledButton onClick={handleLogout}>로그아웃</StyledButton>
            </ButtonContainer>
        </Container>
    );
};

export default MyPage;

// 스타일 컴포넌트들
const Container = styled.div`
    padding: 4rem;
    max-width: 800px;
    margin: auto;
    text-align: center;
`;

const Title = styled.h1`
    font-size: 2.5rem;
    margin-bottom: 3rem;
`;

const ProfileSection = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 3rem;
    position: relative; // 상대 위치 설정
`;

const ProfilePicture = styled.img`
    width: 200px;  // 크기 증가
    height: 200px; // 크기 증가
    border-radius: 50%; // 동그란 이미지
    margin-right: 2rem;
    object-fit: cover; // 이미지 비율 유지
    border: 5px solid #3cb371; // 동그란 테두리 색상
`;

const UserInfo = styled.div`
    text-align: left;
    font-size: 1.2rem; // 크기 감소
`;

const InfoItem = styled.p`
    margin: 0.5rem 0; // 간격 조정
`;

const AboutSection = styled.div`
    margin: 2rem 0;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 1.5rem;
    background-color: #f9f9f9;
    text-align: left; // 왼쪽 정렬
`;

const AboutTitle = styled.h2`
    font-size: 1.8rem;
    margin-bottom: 1rem;
`;

const AboutContent = styled.p`
    font-size: 1.2rem; // 크기 증가
`;

const ButtonContainer = styled.div`
    display: flex;
    justify-content: center;
    gap: 2rem;
`;

const StyledButton = styled.button`
    background-color: #3cb371;
    color: white;
    border: none;
    border-radius: 5px;
    padding: 1rem 2rem; // 크기 증가
    cursor: pointer;
    font-size: 1rem; // 크기 유지
    &:hover {
        background-color: #218838;
    }
`;

const LoadingMessage = styled.p`
    font-size: 1.5rem;
    color: #777;
`;

const UploadButton = styled.button`
    position: absolute; // 절대 위치 설정
    bottom: 10px; // 프로필 사진 아래쪽
    right: 10px; // 프로필 사진 오른쪽
    background-color: #3cb371;
    color: white;
    border: none;
    border-radius: 50%;
    width: 40px; // 버튼 크기
    height: 40px; // 버튼 크기
    cursor: pointer;
    font-size: 1.5rem; // 글자 크기
    display: flex;
    align-items: center;
    justify-content: center;
    &:hover {
        background-color: #218838;
    }
`;
