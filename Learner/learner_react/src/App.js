import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import Header from "./components/Header";
import Courses from "./pages/Courses";
import PostCourse from "./pages/PostCourse";
import PutCourse from "./pages/PutCourse";
import CourseDetail from "./pages/CourseDetail";
import Orders from "./pages/Orders";
import OrderDetail from './pages/OrderDetail';
import OrderCreate from "./pages/OrderCreate";
import OrderUpdate from "./pages/OrderUpdate";
import OrderDelete from "./pages/OrderDelete";
import SignUp from "./pages/SignUp";
import LoginModal from "./components/LoginModal";
import PostCourseInquiry from "./pages/PostCourseInquiry"
import CourseNews from "./pages/CourseNews";
import MyPage from "./pages/MyPage"; // MyPage 컴포넌트 import
import CreateNews from "./pages/CreateNews";
import UpdateNews from "./pages/UpdateNews";
import EditProfile from './components/EditProfile';
import Instructor from "./pages/Instructor";
import CourseReviewCreate from "./pages/course-review/CourseReviewCreate";
import CourseReviewEdit from "./pages/course-review/CourseReviewEdit";
import InstructorReviewCreate from "./pages/instructor-review/InstructorReviewCreate";
import InstructorReviewEdit from "./pages/instructor-review/InstructorReviewEdit";
import ResetPassword from "./components/ResetPassword"; // ResetPassword 컴포넌트 가져오기


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
                <Route path="/" element={<Navigate to="/courses" />} />
                <Route path="/courses" element={<Courses />} />
                <Route path="/post-course" element={<PostCourse />} />
                <Route path="/put-course/:courseId" element={<PutCourse />} />
                <Route path="/courses/:courseId" element={<CourseDetail />} />
                <Route path="/signup" element={<SignUp />} />
                <Route path="/courses/:courseId/post" element={<PostCourseInquiry />} />
                <Route path="/orders" element={<Orders />} />
                <Route path="/orders/:orderId" element={<OrderDetail />} />
                <Route path="/order/create" element={<OrderCreate />} />
                <Route path="/order/update/:orderId" element={<OrderUpdate />} />
                <Route path="/order/Delete/:orderId" element={<OrderDelete />} />
                <Route path="/courses/:courseId/news/:newsId" element={<CourseNews />} />
                <Route path="/내정보" element={<MyPage />} /> {/* MyPage 라우트 추가 */}
                <Route path="/courses/:courseId/news/create" element={<CreateNews />} />
                <Route path="/courses/:courseId/news/:newsId/edit" element={<UpdateNews />} />
                <Route path="/edit-profile" element={<EditProfile />} />
                <Route path="/courses/:courseId/reviews/create" element={<CourseReviewCreate />} />
                <Route path="/courses/:courseId/reviews/:reviewId/edit" element={<CourseReviewEdit />} />
                <Route path="/members/instructor/:nickname" element={<Instructor />} />
                <Route path="/members/instructor/:nickname/reviews/create" element={<InstructorReviewCreate />} /> {/* 강사 리뷰 생성 페이지 */}
                <Route path="/members/instructor/:nickname/reviews/:reviewId" element={<InstructorReviewEdit />} /> {/* 강사 리뷰 수정 페이지 */}
                <Route path="/reset-password/:uuid" element={<ResetPassword />} /> {/* 비밀번호 변경 */}
            </Routes>
        </Router>
    );
}

export default App;

