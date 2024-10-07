import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const VideoNavigation = ({ previousVideo, nextVideo, courseId, currentVideoIndex, isLoading }) => {
    const navigate = useNavigate();

    const handleVideoNavigation = async (video) => {
        if (isLoading || !video) return;

        const youtubeId = extractVideoId(video.url);
        const memberId = localStorage.getItem('memberId');

        try {
            const response = await axios.get(
                `http://localhost:8080/course/${courseId}/purchase?memberId=${memberId}`
            );

            if (response.data) {
                navigate(`/video/${video.video_Id}/play`, {
                    state: {
                        videoEntityId: video.video_Id,
                        youtubeId: youtubeId,
                        courseId: courseId
                    }
                });
            } else {
                alert("이 강의를 구매하지 않았습니다. 주문 페이지로 이동합니다.");
                navigate('/orders');
            }
        } catch (error) {
            console.error("구매 확인 중 오류 발생:", error);
        }
    };

    const extractVideoId = (url) => {
        const regex = /[?&]v=([^&#]*)/;
        const match = url.match(regex);
        return match ? match[1] : null;
    };

    return (
        <div>
            <button onClick={() => handleVideoNavigation(previousVideo)} disabled={currentVideoIndex <= 0}>
                이전 동영상
            </button>
            <button onClick={() => handleVideoNavigation(nextVideo)} disabled={currentVideoIndex < 0 || !nextVideo}>
                다음 동영상
            </button>
        </div>
    );
};

export default VideoNavigation;
