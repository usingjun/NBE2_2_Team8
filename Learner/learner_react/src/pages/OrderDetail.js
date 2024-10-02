import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import styled from "styled-components";

const OrderDetail = () => {
    const { orderId } = useParams(); // URL에서 orderId 가져오기
    console.log(orderId);
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrderDetail = async () => {
            setLoading(true);
            try {
                const response = await axios.get(`http://localhost:8080/order/${orderId}`);
                setOrder(response.data);
            } catch (error) {
                console.error("Error fetching order details:", error);
                setError("장바구니 세부정보를 가져오는 데 실패했습니다.");
            } finally {
                setLoading(false);
            }
        };

        fetchOrderDetail();
    }, [orderId]);


    if (loading) return <p>로딩 중...</p>;
    if (error) return <p>{error}</p>;
    if (!order) return <p>장바구니이 없습니다.</p>;

    return (
        <OrderDetailContainer>
            <h2>장바구니 상세보기</h2>
            <p>장바구니 ID: {order.oId}</p>
            <p>장바구니 상태: {order.orderStatus}</p>
            <p>장바구니 항목:</p>
            <ul>
                {order.orderItemDTOList.map((item, index) => (
                    <li key={index}>
                        강의 ID: {item.courseId}, 가격: {item.price} 원
                    </li>
                ))}
            </ul>
        </OrderDetailContainer>
    );
};

export default OrderDetail;

const OrderDetailContainer = styled.div`
    max-width: 600px;
    margin: 0 auto;
    padding: 1rem;
`;
