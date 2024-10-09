import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import styled from "styled-components";
import { jwtDecode } from "jwt-decode";
import Cookies from "js-cookie";
import { handlePlayClick } from "./HandlePlayClick";

const Course_Url = "http://localhost:8080/course";
const Video_Url = "http://localhost:8080/video";

const VideoList = () => {
    const { courseId } = useParams();
    const [videos, setVideos] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const [role, setRole] = useState(null);
    const [memberNickname, setMemberNickName] = useState(null);

    useEffect(() => {
        const decodeJwt = (token) => {
            try {
                return jwtDecode(token);
            } catch (error) {
                console.error("JWT 디코딩 오류:", error);
                return null;
            }
        };

        const token = Cookies.get("Authorization");
        if (token) {
            const decodedToken = decodeJwt(token);
            setRole(decodedToken?.role);
            setMemberNickName(decodedToken?.mid);
        }

        const fetchVideos = async () => {
            setLoading(true);
            try {
                const response = await axios.get(`${Course_Url}/video/${courseId}`, { withCredentials: true });
                setVideos(response.data);
            } catch (error) {
                console.error("비디오 목록 가져오는 중 오류 발생:", error.response ? error.response.data : error.message);
                setError("비디오 목록을 가져오는 데 실패했습니다.");
            } finally {
                setLoading(false);
            }
        };

        fetchVideos();
    }, [courseId]);

    const handleDeleteClick = async (videoId) => {
        if (window.confirm("정말로 이 비디오를 삭제하시겠습니까?")) {
            try {
                await axios.delete(`${Video_Url}/${videoId}`, { withCredentials: true });
                setVideos(videos.filter(video => video.video_Id !== videoId));
            } catch (error) {
                console.error("비디오 삭제 중 오류 발생:", error);
                setError("비디오를 삭제하는 데 실패했습니다.");
            }
        }
    };

    if (loading) return <LoadingMessage>로딩 중...</LoadingMessage>;
    if (error) return <ErrorMessage>{error}</ErrorMessage>;

    return (
        <Container>
            <Header>비디오 목록</Header>
            {videos.length > 0 ? (
                videos.map((video, index) => (
                    <VideoItem
                        key={video.video_Id}
                        onClick={() => handlePlayClick(courseId, video, navigate, setError, role, memberNickname)}
                    >
                        <VideoInfo>
                            <Title>{index + 1}. {video.description}</Title>
                        </VideoInfo>
                    </VideoItem>
                ))
            ) : (
                <Message>비디오가 없습니다.</Message>
            )}
        </Container>
    );
};

// 스타일 컴포넌트
const Container = styled.div`
    max-width: 700px;
    margin: 0 auto;
    padding: 2rem;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
`;

const Header = styled.h2`
    text-align: center;
    color: #222;
    margin-bottom: 2rem;
    font-size: 1.8rem;
    font-weight: bold;
`;

const Message = styled.p`
    text-align: center;
    color: ${(props) => (props.$error ? "#e74c3c" : "#007bff")};
    font-size: 1.2rem;
`;

const VideoItem = styled.div`
    padding: 1rem;
    border: 1px solid #eee;
    border-radius: 6px;
    margin-bottom: 1rem;
    background-color: #f7f7f7;
    box-shadow: 0 1px 6px rgba(0, 0, 0, 0.05);
    display: flex;
    justify-content: space-between;
    align-items: center;
    cursor: pointer;
`;

const VideoInfo = styled.div`
    flex: 1;
`;

const Title = styled.h3`
    font-size: 1.2rem;
    color: #333;
    margin: 0;
`;

const LoadingMessage = styled.p`
    text-align: center;
    color: #007bff;
    font-size: 1.2rem;
`;

const ErrorMessage = styled.p`
    text-align: center;
    color: red;
    font-weight: bold;
`;

export default VideoList;
