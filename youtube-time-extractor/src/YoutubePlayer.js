import React, { useEffect, useRef, useState } from 'react';

const YoutubePlayer = ({ videoUrl }) => {
    videoUrl = "https://www.youtube.com/watch?v=cJ9xdW_hqR4&list=PLlV7zJmoG4XJfK8vVL2E2NX8ej73vjNlh";
    const playerRef = useRef(null);
    const [totalDuration, setTotalDuration] = useState(0);
    const [currentTime, setCurrentTime] = useState(0);

    const videoId = videoUrl.split('v=')[1]?.split('&')[0];
    const embedUrl = videoId ? `https://www.youtube.com/embed/${videoId}?enablejsapi=1` : '';

    useEffect(() => {
        if (videoId) {
            fetchVideoDuration(videoId).then(duration => {
                setTotalDuration(duration);
                console.log(`동영상 전체 시간: ${duration}초`);
                sendTotalDuration(duration); // 전체 동영상 시간을 백엔드로 전송
            });
        }
    }, [videoUrl]);

    const fetchVideoDuration = (videoId) => {
        const apiKey = 'AIzaSyDoEwQOJ6Igsm9dCnk1b1y1sqzG3qdoEw0'; // 발급받은 API 키
        const url = `https://www.googleapis.com/youtube/v3/videos?id=${videoId}&key=${apiKey}&part=contentDetails`;

        return fetch(url)
            .then(response => response.json())
            .then(data => {
                if (data.items && data.items.length > 0) {
                    const duration = data.items[0].contentDetails.duration;
                    return convertISO8601ToSeconds(duration);
                } else {
                    throw new Error("Video not found");
                }
            });
    };

    const convertISO8601ToSeconds = (duration) => {
        const regex = /PT(\d+H)?(\d+M)?(\d+S)?/;
        const matches = regex.exec(duration);

        const hours = parseInt(matches[1]) || 0;
        const minutes = parseInt(matches[2]) || 0;
        const seconds = parseInt(matches[3]) || 0;

        return hours * 3600 + minutes * 60 + seconds;
    };

    useEffect(() => {
        const script = document.createElement('script');
        script.src = "https://www.youtube.com/iframe_api";
        document.body.appendChild(script);

        window.onYouTubeIframeAPIReady = () => {
            playerRef.current = new window.YT.Player('youtube-player', {
                events: {
                    'onReady': onPlayerReady,
                },
            });
        };

        return () => {
            document.body.removeChild(script);
        };
    }, []);

    const onPlayerReady = (event) => {
        event.target.playVideo();
        setInterval(() => {
            getCurrentTime(); // 현재 시간을 주기적으로 가져오기
        }, 5000);
    };

    const getCurrentTime = () => {
        if (playerRef.current) {
            const currentTime = playerRef.current.getCurrentTime();
            setCurrentTime(currentTime);

            // 현재 재생 시간을 백엔드로 전송
            fetch('http://localhost:8080/api/savePlayTime', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ currentTime: currentTime }),
            })
                .then(response => response.json())
                .then(data => {
                    console.log('현재 재생 시간 전송 성공:', data);
                })
                .catch((error) => {
                    console.error('오류:', error);
                });
        }
    };

    const sendTotalDuration = (duration) => {
        fetch('http://localhost:8080/api/savePlayTime', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ totalDuration: duration }),
        })
            .then(response => response.json())
            .then(data => {
                console.log('전체 동영상 시간 전송 성공:', data);
            })
            .catch((error) => {
                console.error('오류:', error);
            });
    };

    // 전체 동영상 시간과 현재 재생 시간을 가져오는 함수
    const fetchVideoInfo = () => {
        fetch('http://localhost:8080/api/videoInfo')
            .then(response => response.json())
            .then(data => {
                setTotalDuration(data.totalDuration);
                setCurrentTime(data.currentTime);
            })
            .catch((error) => {
                console.error('오류:', error);
            });
    };

    // 컴포넌트가 마운트될 때 비디오 정보를 가져옵니다.
    useEffect(() => {
        fetchVideoInfo();
    }, []);

    if (!videoUrl) {
        return <div>비디오 URL이 필요합니다.</div>;
    }

    return (
        <div>
            <iframe
                id="youtube-player"
                width="560"
                height="315"
                src={embedUrl}
                title="YouTube Video"
                frameBorder="0"
                allowFullScreen
            ></iframe>
            <div>전체 동영상 길이: {totalDuration}초</div>
            <div>현재 동영상 길이: {currentTime.toFixed(2)}초</div>
        </div>
    );
};

export default YoutubePlayer;
