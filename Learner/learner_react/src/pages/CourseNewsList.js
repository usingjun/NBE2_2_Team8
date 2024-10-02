import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function CourseNewsList({ courseId }) {
    const [newsList, setNewsList] = useState([]);

    useEffect(() => {
        fetch(`http://localhost:8080/course/${courseId}/news`)
            .then(res => res.json())
            .then(data => {
                console.log("Fetched news:", data); // Inspect the fetched data
                setNewsList(data.content);
            })
            .catch(err => console.error("새소식 가져오기 실패:", err));
    }, [courseId]);

    return (
        <ul className="list_news">
            {newsList.map(news => (
                <li key={news.newsId}>
                    <Link to={`/course/${courseId}/news/${news.newsId}`}>
                        {news.newsName}
                    </Link>
                </li>
            ))}
        </ul>
    );
}
