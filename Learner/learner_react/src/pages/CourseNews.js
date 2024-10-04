import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import styled from "styled-components";
import { jwtDecode } from "jwt-decode";

export default function CourseNews() {
    const { courseId, newsId } = useParams();
    const navigate = useNavigate();
    const [news, setNews] = useState(null);
    const [liked, setLiked] = useState(false);
    const [userRole, setUserRole] = useState(null);
    const [userName, setUserName] = useState(null);
    const [instructorName, setInstructorName] = useState(null);

    useEffect(() => {
        checkUserRole();
        fetchNewsData();
    }, [courseId, newsId]);

    const checkUserRole = async () => {  // async로 변경
        try {
            const token = document.cookie
                .split('; ')
                .find(row => row.startsWith('Authorization='))
                ?.split('=')[1];

            console.log("토큰:", token);
            if (token) {
                const decodedToken = jwtDecode(token);
                setUserRole(decodedToken.role);
                const email = decodedToken.mid;
                console.log("디코딩된 토큰:", decodedToken);

                // 이메일로 닉네임 가져오기
                const response = await fetch(`http://localhost:8080/member/nickname?email=${email}`);
                if (!response.ok) {
                    throw new Error("닉네임을 가져오는 데 실패했습니다.");
                }
                const nickname = await response.text(); // JSON이 아닌 문자열 반환
                console.log("닉네임:", nickname);
                setUserName(nickname); // 닉네임을 상태에 설정
            }
        } catch (error) {
            console.error("토큰 확인 중 오류 발생:", error);
        }
    };

    const fetchNewsData = () => {
        fetch(`http://localhost:8080/course/${courseId}/news/${newsId}`, {
            credentials: 'include',
        })
            .then(res => {
                if (!res.ok) throw new Error('Failed to fetch news');
                return res.json();
            })
            .then(data => {
                console.log("Fetched news:", data);
                setNews(data);
                setLiked(data.liked);
            })
            .catch(err => {
                console.error("새소식 가져오기 실패:", err);
                alert('새소식을 불러오는데 실패했습니다.');
                navigate(`/courses/${courseId}`);
            });
    };

    const likeNews = () => {
        const method = liked ? 'DELETE' : 'POST';
        fetch(`http://localhost:8080/course/${courseId}/news/${newsId}/like`, {
            method: method,
            credentials: 'include',
        })
            .then(res => {
                if (!res.ok) throw new Error('Failed to update like status');
                return res.json();
            })
            .then(data => {
                console.log(`${liked ? 'Unliked' : 'Liked'} news:`, data);
                setLiked(!liked);
                setNews(prev => ({
                    ...prev,
                    likeCount: liked ? prev.likeCount - 1 : prev.likeCount + 1
                }));
            })
            .catch(err => {
                console.error(`${liked ? '좋아요 취소 실패' : '좋아요 실패'}:`, err);
                alert('좋아요 처리 중 오류가 발생했습니다.');
            });
    };

    const deleteNews = () => {
        if (!window.confirm('정말로 이 새소식을 삭제하시겠습니까?')) {
            return;
        }

        fetch(`http://localhost:8080/course/${courseId}/news/${newsId}`, {
            method: 'DELETE',
            credentials: 'include',
        })
            .then(res => {
                if (!res.ok) throw new Error('Failed to delete news');
                alert('새소식이 성공적으로 삭제되었습니다.');
                navigate(`/courses/${courseId}`);
            })
            .catch(err => {
                console.error("새소식 삭제 실패:", err);
                alert('새소식 삭제 중 오류가 발생했습니다.');
            });
    };

    const canEdit = () => {
        // courseId를 이용해 member_nickname 확인
        fetch(`http://localhost:8080/course/${courseId}/member-nickname`, {
            credentials: 'include',
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error('Network response was not ok');
                }
                return res.text(); // 응답을 텍스트로 처리
            })
            .then(nickname => {
                console.log("Member Nickname:", nickname);
                setInstructorName(nickname); // 직접 문자열로 설정
            })
            .catch(err => console.error("Failed to fetch member nickname:", err));

        return (userRole === 'Role_INSTRUCTOR' && userName===instructorName ) || userRole === 'Role_ADMIN';
    };

    const handleUpdateNews = () => {
        if (canEdit()) {
            navigate(`/courses/${courseId}/news/${newsId}/edit`);
        } else {
            alert('새소식 수정은 강사 또는 관리자만 가능합니다.');
        }
    };


    if (!news) {
        return <div>로딩 중...</div>;
    }

    return (
        <NewsContainer>
            <NewsHeader>{news.newsName}</NewsHeader>
            <NewsMetaInfo>
                <span>작성일: {new Date(news.newsDate).toLocaleDateString()}</span>
                <span>조회수: {news.viewCount}</span>
                <span>좋아요: {news.likeCount}</span>
            </NewsMetaInfo>
            <NewsContent>{news.newsDescription}</NewsContent>
            <NewsFooter>
                <span>마지막 수정일: {new Date(news.lastModifiedDate).toLocaleString()}</span>
                <ButtonContainer>
                    <LikeButton onClick={likeNews} $liked={liked}>
                        {liked ? '좋아요 취소' : '좋아요'}
                    </LikeButton>
                    {canEdit() && (
                        <>
                            <EditButton onClick={handleUpdateNews}>
                                수정하기
                            </EditButton>
                            <DeleteButton onClick={deleteNews}>
                                삭제하기
                            </DeleteButton>
                        </>
                    )}
                </ButtonContainer>
            </NewsFooter>
        </NewsContainer>
    );
}

const ButtonContainer = styled.div`
    display: flex;
    gap: 10px;
`;


const NewsContainer = styled.div`
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const NewsHeader = styled.h2`
    font-size: 24px;
    color: #333;
    margin-bottom: 10px;
`;

const NewsMetaInfo = styled.div`
    display: flex;
    justify-content: space-between;
    color: #666;
    font-size: 14px;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;

    span {
        margin-right: 20px;
        &:last-child {
            margin-right: 0;
        }
    }
`;

const NewsContent = styled.div`
    font-size: 16px;
    line-height: 1.6;
    color: #333;
    margin-bottom: 20px;
    min-height: 200px;
    white-space: pre-wrap;
`;

const NewsFooter = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 12px;
    color: #999;
    padding-top: 10px;
    border-top: 1px solid #eee;
`;

const LikeButton = styled.button`
    padding: 8px 16px;
    background-color: ${props => (props.$liked ? '#ff6b6b' : '#4caf50')};
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.2s;
    font-size: 14px;

    &:hover {
        background-color: ${props => (props.$liked ? '#ff4c4c' : '#45a049')};
    }
`;

const EditButton = styled.button`
padding: 8px 16px;
background-color: #007bff;
color: white;
border: none;
border-radius: 5px;
cursor: pointer;
transition: background-color 0.2s;
font-size: 14px;
margin-left: 10px;

&:hover {
    background-color: #0056b3;
}
`;

const DeleteButton = styled.button`
padding: 8px 16px;
background-color: #dc3545;
color: white;
border: none;
border-radius: 5px;
cursor: pointer;
transition: background-color 0.2s;
font-size: 14px;
margin-left: 10px;

&:hover {
    background-color: #c82333;
}
`;