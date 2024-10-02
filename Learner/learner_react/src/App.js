import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Courses from "./pages/Courses";
import CourseDetail from "./pages/CourseDetail";
import SignUp from "./pages/SignUp";
import LoginModal from "./components/LoginModal";
import PostCourseInquiry from "./pages/PostCourseInquiry"
import CourseNewsList from "./pages/CourseNewsList";
import CourseNews from "./pages/CourseNews";

function App() {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    return (
        <Router>
            <Header openModal={openModal} />
            {isModalOpen && <LoginModal closeModal={closeModal} />}
            <Routes>
                <Route path="/courses" element={<Courses />} />
                <Route path="/courses/:courseId" element={<CourseDetail />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/courses/:courseId/post" element={<PostCourseInquiry />} />

                <Route path="/courses/:courseId/news/:newsId" element={<CourseNews />} />

            </Routes>
        </Router>
    );
}

export default App;
