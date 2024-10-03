import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom"; // Navigate 추가
import Header from "./components/Header";
import Courses from "./pages/Courses";
import CourseDetail from "./pages/CourseDetail";
import Orders from "./pages/Orders";
import OrderDetail from './pages/OrderDetail';
import OrderCreate from "./pages/OrderCreate";
import SignUp from "./pages/SignUp";
import LoginModal from "./components/LoginModal";
import PostCourseInquiry from "./pages/PostCourseInquiry"
import CourseNews from "./pages/CourseNews";
import ReviewEdit from "./pages/ReviewEdit";
import ReviewCreate from "./pages/ReviewCreate";


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
                <Route path="/" element={<Navigate to="/courses" />} /> {/* / 경로에서 /courses로 리다이렉트 */}
                <Route path="/courses" element={<Courses />} />
                <Route path="/courses/:courseId" element={<CourseDetail />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/courses/:courseId/post" element={<PostCourseInquiry />} />
                <Route path="/orders" element={<Orders />} />
                <Route path="/orders/:orderId" component={<OrderDetail/>} />
                <Route path="/order/create" element={<OrderCreate/>} />
                <Route path="/courses/:courseId/news/:newsId" element={<CourseNews />} />
                <Route path="/courses/:courseId/reviews/create" element={<ReviewCreate />} />
                <Route path="/courses/:courseId/reviews/:reviewId/edit" element={<ReviewEdit />} />
            </Routes>
        </Router>
    );
}

export default App;

