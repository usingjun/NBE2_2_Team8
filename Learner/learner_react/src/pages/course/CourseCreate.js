import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const Course_Url = "http://localhost:8080/course";

const CourseCreate = () => {
    const [courseName, setCourseName] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const memberId = localStorage.getItem("memberId");
            await axios.post(Course_Url, { courseName, memberId });
            navigate("/course/list");
        } catch (err) {
            setError("강좌 생성에 실패했습니다.");
        }
    };

    return (
        <Container>
            <h2>강좌 생성</h2>
            {error && <ErrorMessage>{error}</ErrorMessage>}
            <form onSubmit={handleSubmit}>
                <Label>
                    강좌 이름:
                    <Input type="text" value={courseName} onChange={(e) => setCourseName(e.target.value)} required />
                </Label>
                <Button type="submit">생성</Button>
            </form>
        </Container>
    );
};

export default CourseCreate;

const Container = styled.div`
    max-width: 400px;
    margin: 0 auto;
    padding: 2rem;
    background: #f9f9f9;
    border-radius: 8px;
`;

const Label = styled.label`
    display: block;
    margin-bottom: 1rem;
`;

const Input = styled.input`
    width: 100%;
    padding: 0.5rem;
    margin-top: 0.5rem;
`;

const Button = styled.button`
    padding: 0.5rem 1rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
`;

const ErrorMessage = styled.p`
    color: red;
    font-weight: bold;
`;
