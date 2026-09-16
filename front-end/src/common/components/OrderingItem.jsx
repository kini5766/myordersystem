import { Card } from 'react-bootstrap';
import { Link } from "react-router-dom";

const OrderingItem = (props) => {
  const {
    id,
    productId,
    productName,
    sellerName,
    ordererName,
    quantity,
    payment,
    orderStatus
  } = props.ordering;
  return (
    <div>
      <Card>
        <Card.Body>
          <Card.Title>주문번호 : {id}</Card.Title>
          <Card.Title>구매자 : {ordererName}</Card.Title>
          <Card.Title>주문수량 : {quantity}</Card.Title>
          <Card.Title>구매비용 : {payment}</Card.Title>
          <Card.Title>주문상태 : {orderStatus}</Card.Title>
          <hr />
          <Card.Title>제품번호 : {productId}</Card.Title>
          <Card.Title>제품명 : {productName}</Card.Title>
          <Card.Title>판매자 : {sellerName}</Card.Title>
          <Link to={"/product/" + productId} className='btn btn-primary'>제품 상세 보기</Link>
        </Card.Body>
      </Card>
    </div>
  );
};

export default OrderingItem;