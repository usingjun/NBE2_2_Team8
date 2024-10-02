import React, { useState, useEffect } from "react";
import axios from "axios";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const OrderCreate = () => {
    const [orderItems, setOrderItems] = useState([{ courseId: "", price: "" }]);
    const [courses, setCourses] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchCourses = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/course/list");
                console.log("Courses fetched:", response.data); // 추가
                setCourses(response.data);
            } catch (error) {
                console.error("Error fetching courses:", error);
                setError("강의 목록을 가져오는 데 실패했습니다.");
            }
        };

        fetchCourses();
    }, []);


    const handleChange = (index, event) => {
        const values = [...orderItems];
        values[index][event.target.name] = event.target.value;
        setOrderItems(values);
    };

    const handleAddItem = () => {
        setOrderItems([...orderItems, { courseId: "", price: "" }]);
    };

    const handleRemoveItem = (index) => {
        const values = [...orderItems];
        values.splice(index, 1);
        setOrderItems(values);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/api/v1/order", {
                orderItemDTOList: orderItems,
            });
            console.log("Order created:", response.data);
            navigate("/orders");
        } catch (error) {
            console.error("Error creating order:", error);
            setError("주문 생성에 실패했습니다.");
        }
    };

    return (
        <OrderCreateContainer>
            <h2>주문 생성</h2>
            {error && <p style={{ color: "red" }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                {orderItems.map((item, index) => (
                    <ItemContainer key={index}>
                        <select
                            name="courseId"
                            value={item.courseId}
                            onChange={(event) => handleChange(index, event)}
                            required
                        >
                            <option value="">강의 선택</option>
                            {courses.map((course) => (
                                <option key={course.courseId} value={course.courseId}>
                                    {course.courseName} - {course.coursePrice} 원
                                </option>
                            ))}
                        </select>
                        <input
                            type="number"
                            name="price"
                            placeholder="가격"
                            value={item.price}
                            onChange={(event) => handleChange(index, event)}
                            required
                        />
                        <button type="button" onClick={() => handleRemoveItem(index)}>
                            삭제
                        </button>
                    </ItemContainer>
                ))}
                <button type="button" onClick={handleAddItem}>
                    항목 추가
                </button>
                <button type="submit">주문 생성</button>
            </form>
        </OrderCreateContainer>
    );
};

export default OrderCreate;

const OrderCreateContainer = styled.div`
    max-width: 600px;
    margin: 0 auto;
    padding: 1rem;
`;

const ItemContainer = styled.div`
    display: flex;
    gap: 10px;
    margin-bottom: 10px;
`;
