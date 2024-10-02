import React, { useEffect, useState } from "react";
import { useNavigate, Link, useLocation } from "react-router-dom";
import styled from "styled-components";

const Header = ({ openModal }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태

    // 쿠키에서 JWT 토큰 확인
    useEffect(() => {
        console.log("Current cookies:", document.cookie); // 쿠키 확인
        const cookies = document.cookie.split('; ').find(row => row.startsWith('Authorization='));
        if (cookies) {
            const token = cookies.split('=')[1]; // '='를 기준으로 분리하여 토큰 값 추출
            console.log("Extracted token:", token); // 추출한 토큰 확인
            if (token) {
                setIsLoggedIn(true); // 로그인 상태로 변경
            }
        } else {
            console.log("No Authorization cookie found"); // 쿠키가 없을 때 메시지
        }
    }, []);

    // 로그아웃 처리 함수
    const handleLogout = () => {
        document.cookie = "Authorization=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"; // 쿠키 삭제
        setIsLoggedIn(false); // 로그인 상태 변경
        navigate('/courses'); // 메인 페이지로 리다이렉션
    };

    // 현재 페이지가 "/courses"인지 여부 확인
    const isCoursesPage = location.pathname === "/courses";
    const isCourseDetailPage = location.pathname.startsWith("/courses/");

    return (
        <NavBar>
            <HeaderContent $isCoursesPage={isCoursesPage}>
                {/* Learner 로고, 강의, 문의, 검색창 배치 */}
                <LogoWrapper isCoursesPage={isCoursesPage}>
                    <Logo onClick={() => navigate("/courses")}>Learner</Logo>
                </LogoWrapper>

                {/* CourseDetail일 경우에만 강의/문의/검색창 표시 */}
                {isCourseDetailPage && (
                    <LeftSection>
                        <NavItem>강의</NavItem>
                        <NavItem>문의</NavItem>
                        <SearchBar>
                            <input type="text" placeholder="검색해보세요" />
                            <button>🔍</button>
                        </SearchBar>
                    </LeftSection>
                )}

                {/* 로그인/회원가입 버튼 또는 마이페이지/로그아웃 버튼 */}
                <RightSection>
                    {isLoggedIn ? (
                        <>
                            <NavItem onClick={() => navigate('/mypage')}>마이페이지</NavItem>
                            <NavItem onClick={handleLogout}>로그아웃</NavItem>
                        </>
                    ) : (
                        <Menu>
                            <button onClick={openModal}>로그인</button>
                            <Link to="/signup">회원가입</Link>
                        </Menu>
                    )}
                </RightSection>
            </HeaderContent>
        </NavBar>
    );
};

export default Header;

const NavBar = styled.nav`
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #fff;
    padding: 0.5rem 1rem;
    height: 60px;
    position: relative;
    border-bottom: 1px solid #ddd;
    margin: 0 auto;
`;

const HeaderContent = styled.div`
    display: flex;
    justify-content: ${({ $isCoursesPage }) => ($isCoursesPage ? "center" : "space-between")};
    align-items: center;
    width: 100%;
    margin-left: 10rem;
`;

const LogoWrapper = styled.div`
    display: flex;
    justify-content: ${({ isCoursesPage }) => (isCoursesPage ? "center" : "flex-start")};
    flex-grow: 1;
`;

const Logo = styled.h1`
    font-size: 1.5rem;
    color: #3cb371;
    cursor: pointer;
    margin-right: 2rem;
`;

const LeftSection = styled.div`
    display: flex;
    align-items: center;
    gap: 1rem;
    flex-grow: 1;
`;

const RightSection = styled.div`
    display: flex;
    align-items: center;
    gap: 1rem;
`;

const Menu = styled.div`
    display: flex;
    gap: 1rem;
    button {
        background: none;
        border: none;
        font-size: 1rem;
        cursor: pointer;
    }
    a {
        text-decoration: none;
        color: #666;
        font-weight: 500;
        &:hover {
            color: #3cb371;
        }
    }
`;

const NavItem = styled.span`
    cursor: pointer;
    padding: 0.5rem 1rem;
    &:hover {
        color: #3cb371;
    }
`;

const SearchBar = styled.div`
    display: flex;
    align-items: center;
    margin-left: 1rem;
    input {
        padding: 0.5rem;
        border-radius: 20px;
        border: 1px solid #ddd;
        width: 200px;
        margin-right: 0.5rem;
        box-shadow: none; /* 그림자 제거 */
    }
    button {
        background: none;
        border: none;
        font-size: 1.2rem;
        cursor: pointer;
        box-shadow: none; /* 그림자 제거 */
    }
`;
