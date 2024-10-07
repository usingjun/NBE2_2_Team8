import React from 'react';
import { Route, Routes } from 'react-router-dom';
import CourseList from './pages/course/CourseList';
import CourseCreate from './pages/course/CourseCreate';
import CourseUpdate from './pages/course/CourseUpdate';
import CourseDetail from './pages/course/CourseDetail';

const CourseRoutes = () => (
    <Routes>
        <Route path="/course/list" element={<CourseList />} />
        <Route path="/course/create" element={<CourseCreate />} />
        <Route path="/course/update/:courseId" element={<CourseUpdate />} />
        <Route path="/course/:courseId" element={<CourseDetail />} />
    </Routes>
);

export default CourseRoutes;
