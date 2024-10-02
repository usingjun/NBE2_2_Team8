import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom"; // Link import 추가
import axios from "axios";
import styled from "styled-components";

const Orders = () => {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrders = async () => {
            setLoading(true);
            try {
                const response = await axios.get("http://localhost:8080/api/v1/order/list");
                setOrders(response.data);
            } catch (error) {
                console.error("Error fetching orders:", error);
                setError("주문 목록을 가져오는 데 실패했습니다.");
            } finally {
                setLoading(false);
            }
        };

        fetchOrders();
    }, []);

    if (loading) return <p>로딩 중...</p>;
    if (error) return <p>{error}</p>;

    return (
        <OrderList>
            <h2>주문 목록</h2>
            <Link to="/order/create">
                <button>주문 생성</button>
            </Link>
            {orders.length > 0 ? (
                orders.map(order => (
                    <OrderItem key={order.oId}>
                        <p>주문 ID: {order.oid}</p>
                        <p>주문 날짜: {new Date(order.createdDate).toLocaleDateString()}</p>
                        <p>총 금액: {order.totalPrice} 원</p>
                        <Link to={`/orders/${order.oId}`}>상세보기</Link>
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
