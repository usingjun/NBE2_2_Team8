import React, { useState, useEffect } from 'react'; // React 및 훅스 가져오기
import { useNavigate } from 'react-router-dom'; // 페이지 이동을 위한 useNavigate 훅 가져오기
import axios from 'axios'; // HTTP 요청을 위해 axios 가져오기

const InquiryListPage = () => {
    const [inquiries, setInquiries] = useState([]); // 문의 목록 데이터를 저장할 상태
    const [sortBy, setSortBy] = useState('latest'); // 정렬 기준을 저장할 상태, 기본값은 '최신순'
    const [memberId, setMemberId] = useState(null); // LocalStorage에서 가져온 memberId 상태 저장
    const navigate = useNavigate(); // 페이지 이동을 위한 navigate 함수 생성

    // 컴포넌트가 마운트될 때 데이터 및 memberId를 가져오는 useEffect
    useEffect(() => {
        // 문의 목록을 가져오는 비동기 함수
        const fetchInquiries = async () => {
            try {
                const response = await axios.get('http://localhost:8080/inquiries'); // 서버에서 문의 목록 가져오기
                setInquiries(response.data); // 가져온 데이터를 상태에 저장
            } catch (error) {
                // 에러 발생 시 더 자세한 정보를 콘솔에 출력
                console.error('Failed to fetch inquiries:', error.response ? error.response.data : error.message); // 에러의 응답 데이터 또는 메시지를 출력
            }
        };

        // LocalStorage에서 memberId 가져오기
        const storedMemberId = localStorage.getItem('memberId');
        if (storedMemberId) {
            setMemberId(storedMemberId); // memberId를 상태에 저장
        }

        fetchInquiries(); // 문의 목록 가져오는 함수 호출
    }, []); // 빈 배열을 두어 컴포넌트 마운트 시 한 번만 실행되도록 설정

    // 정렬 기준이 변경되면 상태 업데이트하는 함수
    const handleSortChange = (e) => {
        setSortBy(e.target.value); // 선택된 정렬 기준을 상태에 저장
    };

    // 문의 목록을 정렬하는 함수
    const sortedInquiries = inquiries.sort((a, b) => {
        if (sortBy === 'status') {
            return a.inquiryStatus.localeCompare(b.inquiryStatus); // 상태별로 정렬
        } else if (sortBy === 'latest') {
            return new Date(b.inquiryCreateDate) - new Date(a.inquiryCreateDate); // 최신순으로 정렬
        }
        return 0; // 기본적으로 변화 없음
    });

    return (
        <div className="inquiry-list-page">
            <h1>문의 목록</h1>
            <div className="actions">
                <label>
                    {/* 정렬 기준 선택하는 드롭다운 */}
                    정렬 기준:
                    <select value={sortBy} onChange={handleSortChange}>
                        <option value="latest">최신순</option>
                        <option value="status">상태별</option>
                    </select>
                </label>
                {/* memberId가 있을 때만 문의 작성 버튼을 보여줌 */}
                {memberId && (
                    <button onClick={() => navigate('/inquiries/new')}>문의 작성</button>
                )}
            </div>

            {inquiries.length === 0 ? ( // 문의 목록이 비어 있는 경우 조건부 렌더링
                <p>등록된 문의가 없습니다.</p> // 문의가 없을 때 보여줄 문구
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>상태</th>
                        <th>등록일</th>
                        <th>수정일</th>
                    </tr>
                    </thead>
                    <tbody>
                    {/* 문의 목록을 테이블로 표시 */}
                    {sortedInquiries.map((inquiry) => (
                        <tr
                            key={inquiry.inquiryId}
                            onClick={() => navigate(`/inquiries/${inquiry.inquiryId}`)} // 문의 클릭 시 상세 페이지로 이동
                        >
                            <td>{inquiry.inquiryId}</td>
                            <td>{inquiry.inquiryTitle}</td>
                            <td>{inquiry.memberNickname}</td>
                            <td>{inquiry.inquiryStatus}</td>
                            <td>{new Date(inquiry.inquiryCreateDate).toLocaleDateString()}</td>
                            <td>{new Date(inquiry.inquiryUpdateDate).toLocaleDateString()}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default InquiryListPage;
