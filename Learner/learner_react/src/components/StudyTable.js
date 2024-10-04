import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './WeeklyStudyTable.css'; // 스타일 파일 import

const WeeklyStudyTable = ({ memberId }) => {
    const [studyData, setStudyData] = useState([]);
    const [totalStudyTime, setTotalStudyTime] = useState(0);
    const [totalCompleted, setTotalCompleted] = useState(0);
    const [currentDate, setCurrentDate] = useState(new Date());

    // 데이터 fetch 함수
    const fetchData = async () => {
        try {
            const response = await axios.get(`/study-table/${memberId}/weekly-summary`);
            setStudyData(response.data);
            const totalTime = response.data.reduce((acc, data) => acc + data.studyTime, 0);
            const totalCompletedCount = response.data.reduce((acc, data) => acc + data.completed, 0);
            setTotalStudyTime(totalTime);
            setTotalCompleted(totalCompletedCount);
        } catch (error) {
            console.error("데이터를 가져오는 중 오류 발생:", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, [memberId, currentDate]); // currentDate를 dependency로 추가하여 주차 이동 시 재fetch

    const getColorByCompleted = (completed, date) => {
        const today = new Date();
        const isFutureDate = date > today; // 미래 날짜인지 확인

        if (isFutureDate) return 'light-gray'; // 미래 날짜는 연한 회색
        if (completed === 0) return 'default-color';
        else if (completed <= 2) return 'light-green';
        else if (completed <= 5) return 'medium-green';
        else return 'dark-green';
    };

    const handlePreviousWeek = () => {
        const previousWeek = new Date(currentDate);
        previousWeek.setDate(currentDate.getDate() - 7);
        setCurrentDate(previousWeek);
    };

    const handleNextWeek = () => {
        const nextWeek = new Date(currentDate);
        nextWeek.setDate(currentDate.getDate() + 7);
        setCurrentDate(nextWeek);
    };

    const getWeekInfo = (date) => {
        const year = date.getFullYear();
        const month = date.getMonth() + 1; // 0부터 시작하므로 +1

        // 주 시작일(일요일)로 설정
        const firstDayOfWeek = new Date(date);
        firstDayOfWeek.setDate(date.getDate() - date.getDay()); // 일요일로 설정

        // 주차 계산 (첫째 주가 1로 시작)
        const week = Math.ceil((firstDayOfWeek.getDate() + 1) / 7);
        return `${year}년 ${month}월 ${week}주차`;
    };

    return (
        <div className="weekly-study-table">
            <h2>주간 학습</h2>
            <div className="week-navigation">
                <button onClick={handlePreviousWeek}>◀</button>
                <span>{getWeekInfo(currentDate)}</span>
                <button onClick={handleNextWeek}>▶</button>
            </div>
            <div className="circle-area">
                <div className="circle-container">
                    {['일', '월', '화', '수', '목', '금', '토'].map((day, index) => {
                        const date = new Date(currentDate);
                        date.setDate(date.getDate() - date.getDay() + index); // 해당 요일의 날짜 계산

                        return (
                            <div key={index} className="day-circle-wrapper">
                                <div
                                    className={`circle ${getColorByCompleted(studyData[index]?.completed || 0, date)}`}
                                    title={`${day}: ${studyData[index]?.completed || 0} 완료`}
                                />
                                <div className="day-label">{day}</div>
                            </div>
                        );
                    })}
                </div>
            </div>
            <div className="summary">
                <h3>총 학습 시간: {totalStudyTime} 시간</h3>
                <h3>총 완료한 수업: {totalCompleted} 수업</h3>
            </div>
        </div>
    );
};

export default WeeklyStudyTable;
