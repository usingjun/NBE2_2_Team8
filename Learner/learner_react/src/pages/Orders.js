import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import styled from "styled-components";

const Order_Url = "http://localhost:8080/order";

const Orders = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const memberId = "1"; // 하드코딩된 멤버 ID 수정 필요
    const navigate = useNavigate();

    useEffect(() => {
        const fetchOrders = async () => {
            setLoading(true);
            try {
                const response = await axios.get(`${Order_Url}/list/${memberId}`);
                setOrders(response.data);
            } catch (error) {
                console.error("주문 가져오는 중 오류 발생:", error);
                setError("주문 목록을 가져오는 데 실패했습니다.");
            } finally {
                setLoading(false);
            }
        };
        fetchOrders();
    }, [memberId]);

    const handleUpdateClick = (orderId) => {
        navigate(`/orders/update/${orderId}`);
    };

    if (loading) return <p>로딩 중...</p>;
    if (error) return <p>{error}</p>;

    return (
        <OrderList>
            <h2>주문 목록</h2>
            <Link to="/order/create">
                <StyledButton>주문 생성</StyledButton>
            </Link>
            {orders.length > 0 ? (
                orders.map(order => (
                    <OrderItem key={order.orderId}>
                        <p>주문 ID: {order.orderId}</p>
                        <p>주문 날짜: {new Date(order.createdDate).toLocaleDateString()}</p>
                        <p>총 금액: {order.totalPrice} 원</p>
                        <button onClick={() => handleUpdateClick(order.orderId)}>수정</button>
                        <Link to={`/orders/${order.orderId}`}>상세보기</Link>
                    </OrderItem>
                ))
            ) : (
                <p>주문이 없습니다.</p>
            )}
        </OrderList>
    );
};

export default Orders;

const OrderList = styled.div`
    max-width: 800px;
    margin: 0 auto;
    padding: 1rem;
`;

const OrderItem = styled.div`
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    margin-bottom: 1rem;
`;

const StyledButton = styled.button`
    padding: 0.5rem 1rem;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;

    &:hover {
        background-color: #0056b3;
    }
`;
