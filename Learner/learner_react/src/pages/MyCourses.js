import React, { useEffect, useState } from 'react';

const MyCourses = () => {
    const [courses, setCourses] = useState([]);

    useEffect(() => {
        // 여기에 서버에서 수강 정보를 가져오는 로직을 추가하세요.
        fetch('/api/my-courses')
            .then(response => response.json())
            .then(data => setCourses(data))
            .catch(error => console.error('Error fetching courses:', error));
    }, []);

    return (
        <div>
            <h1>내 수강 정보</h1>
            <ul>
                {courses.map(course => (
                    <li key={course.id}>{course.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default MyCourses;
