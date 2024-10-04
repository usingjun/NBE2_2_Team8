import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import styled from "styled-components";

const OrderUpdate = () => {
    const { orderId } = useParams(); // URL 파라미터에서 orderId 가져오기
    const [order, setOrder] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchOrder = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/order/${orderId}`);
                setOrder(response.data);
            } catch (error) {
                console.error("Error fetching order:", error);
                setError("주문 정보를 가져오는 데 실패했습니다.");
            }
        };

        fetchOrder();
    }, [orderId]);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setOrder(prevState => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.put(`http://localhost:8080/order/${orderId}/`, order);
            console.log("Order updated:", response.data);
            navigate(`/orders/${orderId}`); // 업데이트 후 상세보기 페이지로 이동
        } catch (error) {
            console.error("Error updating order:", error);
            setError("주문 업데이트에 실패했습니다.");
        }
    };

    if (!order) return <p>로딩 중...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;

    return (
        <OrderUpdateContainer>
            <h2>주문 업데이트</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>주문 상태:</label>
                    <select name="orderStatus" value={order.orderStatus} onChange={handleChange} required>
                        <option value="PENDING">대기</option>
                        <option value="COMPLETED">완료</option>
                        <option value="CANCELLED">취소</option>
                    </select>
                </div>
                {/* 필요 시 다른 필드 추가 가능 */}
                <button type="submit">주문 업데이트</button>
            </form>
        </OrderUpdateContainer>
    );
};

export default OrderUpdate;

const OrderUpdateContainer = styled.div`
    max-width: 600px;
    margin: 0 auto;
    padding: 1rem;
`;
