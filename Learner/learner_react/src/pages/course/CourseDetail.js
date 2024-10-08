import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import styled from "styled-components";
import CourseNewsList from "../CourseNewsList";
import CourseReview from "../course-review/CourseReview";
import VideoList from "../video/VideoList"; // VideoList 컴포넌트 임포트
import CourseInquiryList from "../CourseInquiryList";

// 기본 이미지 경로
const defaultImage = "/images/course_default_img.png";

const CourseDetail = () => {
    const { courseId } = useParams(); // URL에서 courseId 가져오기
    const navigate = useNavigate(); //useNavigate 추가
    const [course, setCourse] = useState(null);
    const [activeTab, setActiveTab] = useState("curriculum");


    useEffect(() => {
        // courseId를 이용해서 해당 강의 정보를 가져옴
        axios.get(`http://localhost:8080/course/${courseId}`)
            .then((response) => {
                setCourse(response.data); // 강의 데이터 설정
            })
            .catch((error) => {
                console.error("Error fetching the course data:", error);
            });
    }, [courseId]);


    const handleTabChange = (tab) => {
        setActiveTab(tab);
    };

    return (
        <DetailPage>
            {/* 강의 상세 정보 */}
            {course && (
                <CourseInfo>
                    <CourseImage src={defaultImage} alt="Course Image"/>
                    <CourseDetails>
                        <CourseTitle>{course.courseName}</CourseTitle>
                        <CourseDescription>{course.courseDescription}</CourseDescription>
                        <Instructor>
                            <InstructorName>강사 : {course.memberNickname}</InstructorName>
                            <StyledButton onClick={() => navigate(`/members/instructor/${course.memberNickname}`)}>강사 페이지</StyledButton>
                        </Instructor>

                    </CourseDetails>
                </CourseInfo>
            )}

            {/* 구분선 */}
            <Separator/>

            {/* 탭 메뉴 */}
            <TabMenu>
                <Tab onClick={() => handleTabChange("curriculum")} active={activeTab === "curriculum"}>
                    커리큘럼
                </Tab>
                <Tab onClick={() => handleTabChange("reviews")} active={activeTab === "reviews"}>
                    수강평
                </Tab>
                <Tab onClick={() => handleTabChange("questions")} active={activeTab === "questions"}>
                    강의 문의
                </Tab>
                <Tab onClick={() => handleTabChange("news")} active={activeTab === "news"}>
                    새소식
                </Tab>
            </TabMenu>

            <Separator/>

            {/* 탭에 따라 내용 변경 */}
            <TabContent>
                {activeTab === "curriculum" && <VideoList /> } {/* VideoList 컴포넌트 추가 */}
                {activeTab === "reviews" && (
                    <>
                        <CourseReview courseId={courseId}/>
                    </>
                )}

                {activeTab === "questions" && <CourseInquiryList courseId={courseId} />}
                {activeTab === "news" && <CourseNewsList courseId={courseId} />}
            </TabContent>
        </DetailPage>
    );
};

export default CourseDetail;

// 스타일 컴포넌트들

const DetailPage = styled.div`
    max-width: 1200px;
    margin: 0 auto;
    padding: 1rem;
`;

const CourseInfo = styled.div`
    display: flex;
    margin-top: 2rem;
`;

const CourseImage = styled.img`
    width: 250px;
    height: 150px;
    object-fit: cover;
    border-radius: 10px;
`;

const CourseDetails = styled.div`
    margin-left: 2rem;
    display: flex;
    flex-direction: column;
`;

const CourseTitle = styled.h2`
    font-size: 1.8rem;
`;

const CourseDescription = styled.p`
    margin: 0.5rem 0;
`;

const Instructor = styled.p`
    font-size: 1rem;
    color: #555;
    margin-top: 1rem; /* 위쪽 여백 추가 */
    display: flex;
    align-items: center; /* 세로 정렬 */
`;

const InstructorName = styled.span`
    margin-right: 1rem; /* 버튼과의 간격 추가 */
`;

const TabMenu = styled.div`
    display: flex;
    justify-content: space-between;
    margin-top: 1rem;
`;

const Tab = styled.button`
    padding: 1rem 2rem;
    background-color: ${(props) => (props.active ? "#3cb371" : "#f9f9f9")};  /* 기본 배경색 설정 */
    border: none;
    border-bottom: ${(props) => (props.active ? "2px solid #3cb371" : "2px solid #ddd")};  /* 탭 활성화 시 구분선 */
    font-size: 1rem;
    color: ${(props) => (props.active ? "#fff" : "#333")};  /* 활성화된 탭의 글자색 */
    cursor: pointer;
    &:hover {
        background-color: #3cb371;  /* 호버 시 배경색 변경 */
        color: #fff;  /* 호버 시 글자색 변경 */
        border-bottom: 2px solid #3cb371;
    }
`;


const TabContent = styled.div`
    margin-top: 1rem;
    padding: 1rem;
    background-color: #f9f9f9;
    border-radius: 10px;
`;

const Separator = styled.div`
    height: 1px;
    background-color: #ddd;
    margin: 0; /* 구분선 간격 조정 */
`;

const StyledButton = styled.button`
    padding: 0.75rem 1.5rem; /* 상하, 좌우 패딩 */
    background-color: #3cb371; /* 버튼 기본 색상 */
    color: white; /* 글자 색상 */
    border: none; /* 경계선 없음 */
    border-radius: 8px; /* 둥근 모서리 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
    font-size: 1rem; /* 글자 크기 */
    cursor: pointer; /* 마우스 포인터 */
    transition: background-color 0.3s, transform 0.3s; /* 애니메이션 효과 */

    &:hover {
        background-color: #2a9d63; /* 호버 시 색상 변경 */
        transform: translateY(-2px); /* 호버 시 약간 위로 이동 */
    }

    &:active {
        transform: translateY(0); /* 클릭 시 원래 위치로 돌아옴 */
    }
`;