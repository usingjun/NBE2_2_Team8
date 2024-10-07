import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import styled from "styled-components";

const Course_Url = "http://localhost:8080/course";

const CourseDetail = () => {
    const { courseId } = useParams();
    const [course, setCourse] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCourse = async () => {
            try {
                const response = await axios.get(`${Course_Url}/${courseId}`);
                setCourse(response.data);
            } catch (err) {
                setError("강좌를 불러오는 데 실패했습니다.");
            }
        };
        fetchCourse();
    }, [courseId]);

    if (error) return <ErrorMessage>{error}</ErrorMessage>;

    return (
        <Container>
            {course ? (
                <>
                    <h2>강좌 상세 정보</h2>
                    <p><strong>강좌 ID:</strong> {course.courseId}</p>
                    <p><strong>강좌 이름:</strong> {course.courseName}</p>
                    <Button onClick={() => navigate(`/course/update/${courseId}`)}>수정하기</Button>
                </>
            ) : (
                <LoadingMessage>로딩 중...</LoadingMessage>
            )}
        </Container>
    );
};

export default CourseDetail;

const Container = styled.div`
    max-width: 400px;
    margin: 0 auto;
    padding: 2rem;
    background: #f9f9f9;
    border-radius: 8px;
`;

const Button = styled.button`
    padding: 0.5rem 1rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
`;

const LoadingMessage = styled.p`
    text-align: center;
    color: #007bff;
`;

const ErrorMessage = styled.p`
    text-align: center;
    color: red;
    font-weight: bold;
`;
