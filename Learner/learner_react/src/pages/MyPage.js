import React, { useEffect, useState } from "react";
import styled from "styled-components";

const MyPage = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [myCourses, setMyCourses] = useState([]); // 사용자 강의 상태 추가
    const [selectedFile, setSelectedFile] = useState(null);
    const [isHover, setIsHover] = useState(false);

    useEffect(() => {
        const memberId = localStorage.getItem("memberId");

        const fetchUserInfo = async () => {
            try {
                const response = await fetch(`http://localhost:8080/members/${memberId}`, {
                    credentials: "include",
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

        const fetchMyCourses = async () => {
            try {
                const response = await fetch(`http://localhost:8080/members/${memberId}/courses`, {
                    credentials: "include",
                });
                if (response.ok) {
                    const data = await response.json();
                    setMyCourses(data); // 사용자 강의 정보 설정
                } else {
                    console.error("강의 정보 로드 실패:", response.status);
                }
            } catch (error) {
                console.error("강의 정보 API 호출 중 오류 발생:", error);
            }
        };

        fetchUserInfo();
        fetchMyCourses(); // 강의 정보 가져오기
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
            const memberId = localStorage.getItem("memberId");
            const formData = new FormData();
            formData.append("file", file);
            try {
                const response = await fetch(`http://localhost:8080/${memberId}/image`, {
                    method: "POST",
                    body: formData,
                    credentials: "include",
                });
                if (response.ok) {
                    const data = await response.json();
                    setUserInfo((prevUserInfo) => ({
                        ...prevUserInfo,
                        profileImage: data.profileImage,
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
        fileInput.click();
    };

    if (!userInfo) {
        return <LoadingMessage>로딩 중...</LoadingMessage>;
    }

    const profileImageSrc = userInfo.profileImage
        ? `data:image/jpeg;base64,${userInfo.profileImage}`
        : "http://localhost:8080/images/default_profile.jpg";

    return (
        <Container>
            <Title>마이페이지</Title>
            <ProfileSection
                onMouseEnter={() => setIsHover(true)}
                onMouseLeave={() => setIsHover(false)}
            >
                <ProfilePicture src={profileImageSrc} alt="Profile" />
                {isHover && (
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
            <MyCoursesSection>
                <AboutTitle>내 강의 목록</AboutTitle>
                {myCourses.length > 0 ? (
                    <CourseList>
                        {myCourses.map(course => (
                            <CourseItem key={course.courseId}>
                                {course.courseName}
                            </CourseItem>
                        ))}
                    </CourseList>
                ) : (
                    <p>등록된 강의가 없습니다.</p>
                )}
            </MyCoursesSection>
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
    position: relative;
`;

const ProfilePicture = styled.img`
    width: 200px;
    height: 200px;
    border-radius: 50%;
    margin-right: 2rem;
    object-fit: cover;
    border: 5px solid #3cb371;
`;

const UserInfo = styled.div`
    text-align: left;
    font-size: 1.2rem;
`;

const InfoItem = styled.p`
    margin: 0.5rem 0;
`;

const AboutSection = styled.div`
    margin: 2rem 0;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 1.5rem;
    background-color: #f9f9f9;
    text-align: left;
`;

const AboutTitle = styled.h2`
    font-size: 1.8rem;
    margin-bottom: 1rem;
`;

const AboutContent = styled.p`
    font-size: 1.2rem;
`;

const MyCoursesSection = styled.div`
    margin: 2rem 0;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 1.5rem;
    background-color: #f9f9f9;
    text-align: left;
`;

const CourseList = styled.ul`
    list-style-type: none;
    padding: 0;
`;

const CourseItem = styled.li`
    padding: 0.5rem 0;
    border-bottom: 1px solid #ddd;
    &:last-child {
        border-bottom: none; // 마지막 항목의 경계 제거
    }
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
    padding: 1rem 2rem;
    cursor: pointer;
    font-size: 1rem;
    &:hover {
        background-color: #218838;
    }
`;

const LoadingMessage = styled.p`
    font-size: 1.5rem;
    color: #777;
`;

const UploadButton = styled.button`
    position: absolute;
    bottom: 10px;
    right: 10px;
    background-color: #3cb371;
    color: white;
    border: none;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    cursor: pointer;
    font-size: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: center;
    &:hover {
        background-color: #218838;
    }
`;
