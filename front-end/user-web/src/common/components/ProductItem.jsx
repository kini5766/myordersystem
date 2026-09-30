import { Card } from 'react-bootstrap';
import { Link } from "react-router-dom";

const ProductItem = (props) => {
  const { id, name, price, stockQuantity, memberName } = props.product;
  return (
    <div>
      <Card>
        <Card.Body>
          <Card.Title>제품번호 : {id}</Card.Title>
          <Card.Title>제품명 : {name}</Card.Title>
          <Card.Title>가격 : {price}</Card.Title>
          <Card.Title>재고 : {stockQuantity}</Card.Title>
          <Card.Title>판매자 : {memberName}</Card.Title>
          <Link to={"/product/" + id} className='btn btn-primary'>제품 상세 보기</Link>
        </Card.Body>
      </Card>
    </div>
  );
};

export default ProductItem;