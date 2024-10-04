import React, { useEffect, useState } from "react";
import { useNavigate, Link, useLocation } from "react-router-dom";
import styled from "styled-components";

const Header = ({ openModal }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태
    const [isMenuOpen, setIsMenuOpen] = useState(false); // 하위 메뉴 상태

    // 쿠키에서 JWT 토큰 확인
    useEffect(() => {
        const cookies = document.cookie.split('; ').find(row => row.startsWith('Authorization='));
        if (cookies) {
            const token = cookies.split('=')[1];
            if (token) {
                setIsLoggedIn(true);
            }
        }
    }, []);

    // 로그아웃 처리 함수
    const handleLogout = () => {
        document.cookie = "Authorization=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        setIsLoggedIn(false);
        navigate('/courses');
    };

    // 현재 페이지가 "/courses"인지 여부 확인
    const isCoursesPage = location.pathname === "/courses";
    const isCourseDetailPage = location.pathname.startsWith("/courses/");

    return (
        <NavBar>
            <HeaderContent $isCoursesPage={isCoursesPage}>
                <LogoWrapper $isCoursesPage={isCoursesPage}>
                    <Logo onClick={() => navigate("/courses")}>Learner</Logo>
                </LogoWrapper>

                {isCourseDetailPage && (
                    <LeftSection>
                        <NavItem onClick={() => navigate("/courses")}>강의</NavItem>
                        <NavItem>문의</NavItem>
                        <SearchBar>
                            <input type="text" placeholder="검색해보세요" />
                            <button>🔍</button>
                        </SearchBar>
                    </LeftSection>
                )}

                <RightSection>
                    {isLoggedIn ? (
                        <>
                            <NavItem onClick={() => setIsMenuOpen(!isMenuOpen)}>마이페이지</NavItem>
                            {isMenuOpen && (
                                <SubMenu>
                                    <SubMenuItem onClick={() => navigate('/myinfo')}>내정보</SubMenuItem>
                                    <SubMenuItem onClick={() => navigate('/cart')}>장바구니</SubMenuItem>
                                    <SubMenuItem onClick={() => navigate('/edit-profile')}>회원정보 수정</SubMenuItem>
                                    <SubMenuItem onClick={handleLogout}>로그아웃</SubMenuItem>
                                </SubMenu>
                            )}
                        </>
                    ) : (
                        <Menu>
                            <StyledButton onClick={openModal}>로그인</StyledButton>
                            <Link to="/signup">
                                <StyledButton>회원가입</StyledButton>
                            </Link>
                        </Menu>
                    )}
                </RightSection>
            </HeaderContent>
        </NavBar>
    );
};

export default Header;

// 스타일 컴포넌트들 그대로 유지

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
    overflow: visible; /* Header 범위를 넘어가는 내용을 보이게 함 */
`;

const HeaderContent = styled.div`
    display: flex;
    justify-content: ${({ $isCoursesPage }) => ($isCoursesPage ? "center" : "space-between")};
    align-items: center;
    width: 100%;
    max-width: 1200px; /* Header 최대 너비 설정 */
    margin: 0 auto; /* 중앙 정렬 */
    overflow: visible; /* HeaderContent 범위를 넘어가는 내용을 보이게 함 */
`;

const LogoWrapper = styled.div`
    display: flex;
    justify-content: ${({ $isCoursesPage }) => ($isCoursesPage ? "center" : "flex-start")};
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
`;

const StyledButton = styled.button`
    background-color: #3cb371;
    color: white;
    border: 2px solid #3cb371;
    border-radius: 5px;
    padding: 0.5rem 1rem;
    cursor: pointer;
    &:hover {
        background-color: white;
        color: #3cb371;
    }
`;

const NavItem = styled.span`
    cursor: pointer;
    padding: 0.5rem 1rem;
    position: relative;
    border-radius: 5px; /* 테두리 둥글게 */
    background-color: ${({ $isActive }) => ($isActive ? '#3cb371' : 'transparent')}; /* 마이페이지 활성화 시 배경색 */
    color: ${({ $isActive }) => ($isActive ? 'white' : 'inherit')}; /* 마이페이지 활성화 시 글자색 변경 */
    &:hover {
        background-color: #3cb371; /* 마우스 오버 시 배경색 */
        color: white; /* 마우스 오버 시 글자색 */
    }
`;

const SubMenu = styled.div`
    position: absolute;
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-top: 0.5rem;
    z-index: 1000; /* 메뉴가 다른 요소 위에 나타나도록 설정 */
`;

const SubMenuItem = styled(NavItem)`
    padding: 0.5rem 1rem;
    &:hover {
        background-color: #f0f0f0;
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
        box-shadow: none;
    }
    button {
        background: none;
        border: none;
        font-size: 1.2rem;
        cursor: pointer;
        box-shadow: none;
    }
`;
