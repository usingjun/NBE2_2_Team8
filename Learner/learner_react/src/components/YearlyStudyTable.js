import React, { useEffect, useState } from 'react'; // React 및 훅을 가져옵니다.
import axios from 'axios'; // axios 라이브러리를 가져와 API 요청에 사용합니다.

const YearlyStudyTable = ({ memberId }) => { // YearlyStudyTable 컴포넌트를 정의하며, memberId를 props로 받습니다.
    const [year, setYear] = useState(new Date().getFullYear()); // 현재 연도로 초기화된 상태 변수 year
    const [yearlySummary, setYearlySummary] = useState([]); // 연도별 요약 데이터를 저장할 상태 변수
    const [totalCompleted, setTotalCompleted] = useState(0); // 완료한 수업의 총 개수를 저장할 상태 변수
    const [totalStudyTime, setTotalStudyTime] = useState(0); // 총 학습 시간을 저장할 상태 변수

    // 데이터 fetch 함수
    const fetchData = async (year) => { // 연도를 인자로 받아 API 호출을 통해 데이터를 가져오는 비동기 함수
        try {
            const memberId = 1;
            const response = await axios.get(`http://localhost:8080/study-tables/${memberId}/yearly-summary?year=${year}`); // API 요청
            const { yearlyCompleted, yearlyStudyTime, yearlySummary } = response.data; // 응답 데이터 구조 분해
            setTotalCompleted(yearlyCompleted); // 완료한 수업 수 업데이트
            setTotalStudyTime(yearlyStudyTime); // 총 학습 시간 업데이트
            setYearlySummary(yearlySummary); // 연도별 요약 데이터 업데이트
        } catch (error) {
            console.error("데이터를 가져오는 중 오류 발생:", error); // 에러 발생 시 콘솔에 에러 로그
        }
    };

    useEffect(() => {
        fetchData(year); // 컴포넌트가 마운트될 때와 year가 변경될 때 fetchData 호출
    }, [year]); // year가 변경될 때마다 이펙트가 실행됨

    const handlePreviousYear = () => { // 이전 연도로 이동하는 함수
        setYear(year - 1); // year 상태를 1 감소시킴
    };

    const handleNextYear = () => { // 다음 연도로 이동하는 함수
        setYear(year + 1); // year 상태를 1 증가시킴
    };

    const getColorByCompleted = (completed) => { // 완료한 수업 수에 따라 색상을 반환하는 함수
        if (completed === 0) return 'white'; // 0개일 경우 흰색
        else if (completed <= 5) return 'lightgreen'; // 1~5개일 경우 연한 초록색
        else if (completed <= 10) return 'mediumgreen'; // 6~10개일 경우 중간 초록색
        else if (completed <= 20) return 'darkgreen'; // 11~20개일 경우 진한 초록색
        else return 'black'; // 20개 초과일 경우 검은색
    };

    return (
        <div className="yearly-study-table"> {/* 컴포넌트 최상위 div */}
            <h2>연간 학습</h2> {/* 제목 */}
            <div className="year-navigation"> {/* 연도 변경을 위한 버튼 영역 */}
                <button onClick={handlePreviousYear}>이전 연도</button> {/* 이전 연도 버튼 */}
                <span>{year}</span> {/* 현재 연도 표시 */}
                <button onClick={handleNextYear}>다음 연도</button> {/* 다음 연도 버튼 */}
            </div>
            <table> {/* 학습 정보를 보여줄 테이블 */}
                <thead> {/* 테이블 헤더 */}
                <tr> {/* 행 시작 */}
                    <th>주차</th> {/* 주차 열 */}
                    {[...Array(12)].map((_, index) => ( // 12개의 월을 위한 반복문
                        <th key={index}>{index + 1}월</th> // 각 월을 헤더로 추가
                    ))}
                </tr> {/* 행 끝 */}
                </thead>
                <tbody> {/* 테이블 본문 */}
                {[...Array(5)].map((_, weekIndex) => ( // 5주차를 위한 반복문
                    <tr key={weekIndex}> {/* 주차 행 */}
                        <td>{weekIndex + 1}주차</td> {/* 주차 표시 */}
                        {[...Array(12)].map((_, monthIndex) => { // 각 월에 대한 반복문
                            const completed = yearlySummary.find(([month, week]) => month === monthIndex + 1 && week === weekIndex + 1) || [0, 0]; // 해당 주차와 월의 완료 수업 개수 찾기
                            return (
                                <td key={monthIndex} style={{ backgroundColor: getColorByCompleted(completed[1]) }}> {/* 완료 수에 따라 배경색 설정 */}
                                    {/* 완료된 수업 수는 더 이상 표시하지 않음 */}
                                </td>
                            );
                        })}
                    </tr>
                ))}
                </tbody>
            </table>
            <div className="completion-summary"> {/* 완료한 수업 수 표시 영역 */}
                <h3>완료한 수업 수: {totalCompleted}</h3> {/* 완료한 수업 수 텍스트 */}
                <div style={{ backgroundColor: getColorByCompleted(totalCompleted) }}> {/* 총 완료 수에 따른 색상 표시 */}
                    {totalCompleted}개에 따른 색상
                </div>
            </div>
            <div className="summary"> {/* 총 학습 시간 표시 영역 */}
                <h3>총 학습 시간: {totalStudyTime} 시간</h3> {/* 총 학습 시간 텍스트 */}
            </div>
            <style jsx>{`
                .yearly-study-table {
                    padding: 20px; // 패딩 설정
                }
                .year-navigation {
                    display: flex; // 플렉스 박스 레이아웃 사용
                    justify-content: space-between; // 요소 간 간격 조정
                    margin-bottom: 20px; // 하단 마진 설정
                }
                table {
                    width: 100%; // 테이블 너비 100%
                    border-collapse: collapse; // 테두리 중첩 방지
                }
                th, td {
                    border: 1px solid #ccc; // 테두리 색상 및 두께
                    padding: 10px; // 패딩 설정
                    text-align: center; // 텍스트 중앙 정렬
                }
                .completion-summary {
                    margin-top: 20px; // 상단 마진 설정
                }
            `}</style>
        </div>
    );
};

export default YearlyStudyTable; // YearlyStudyTable 컴포넌트를 내보냅니다.
