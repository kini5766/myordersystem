import { useEffect, useState } from 'react';
import { Button, Card, Container } from 'react-bootstrap';
import Form from 'react-bootstrap/Form';
import { useNavigate, useParams } from 'react-router-dom';
import { fetchWithAccess, hasAccess, isMine } from "../../util/fetchUtil";

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const ProductDetail = (props) => {
  const propsParam = useParams();
  const productId = propsParam.productId;
  const navigate = useNavigate();

  const [product, setProduct] = useState({
    id: 0,
    name: '',
    price: 0,
    stockQuantity: 0,
    memberId: 0,
    memberName: ''
  });

  const [productCount, setProductCount] = useState(1);

  const changeProductCount = e => setProductCount(e.target.value);

  const refreshProduct = () => {
    fetch(`${BACKEND_API_BASE_URL}/product-service/product/detail/${productId}`)
      .then((res) => res.json())
      .then((res) => {
        setProduct(res);
      })
      .catch(error => {
        console.log("실패", error);
        setProduct(null);
      });
  }

  useEffect(refreshProduct, []);

  const updateProduct = () => {
    navigate(`/product/${productId}/modify`);
  };

  const deleteProduct = () => {
    fetchWithAccess(`${BACKEND_API_BASE_URL}/product-service/product/delete/${productId}`, {
      method: "DELETE",
    })
      .then((res) => res.text())
      .then((res) => {
        console.log(res);
        if (res === 'Success') {
          navigate('/myProductList');
        } else {
          alert('삭제 실패');
        }
      })
      .catch(error => {
        console.log("실패", error);
        alert('삭제 실패');
      });
  }

  const buyProduct = e => {
    e.preventDefault();

    fetchWithAccess(`${BACKEND_API_BASE_URL}/ordering-service/ordering/create`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf8"
      },
      body: JSON.stringify({ productId, productCount })
    }).then(res => {
      if (res.status === 201) {
        return res.json();
      } else {
        return null;
      }
    }).then(res => {
      refreshProduct();
      if (res != null) {
        alert('제품 구매 성공했습니다!!');
      } else {
        alert('제품 구매 실패했습니다!!');
      }
    }).catch(error => {
      console.log("실패", error);
      alert('제품 구매 실패했습니다!!');
    });
  }

  return (
    <div>
      <Container>
        <br /><hr />
        <h3>상세보기</h3>
        {product !== null ? <Card>
          <Card.Body>
            <Card.Title>제품번호 : {product.id}</Card.Title>
            <Card.Title>제품명 : {product.name}</Card.Title>
            <Card.Title>가격 : {product.price}</Card.Title>
            <Card.Title>재고 : {product.stockQuantity}</Card.Title>
            <Card.Title>판매자 : {product.memberName}</Card.Title>
          </Card.Body>
        </Card> : <>제품이 존재하지 않습니다.</>}
        {product !== null && isMine(product.memberId) && (
          <>
            <br /><hr />
            <Button variant='warning' onClick={updateProduct}>수정</Button>
            {' '}
            <Button variant="warning" onClick={deleteProduct}>삭제</Button>
          </>
        )}
        {product !== null && hasAccess() && (
          <>
            <br /><hr />
            <Form className="d-flex" onSubmit={buyProduct}>
              <Form.Control
                type="number"
                placeholder="구매 수량"
                className="me-2"
                onChange={changeProductCount}
                value={productCount}
              />
              <Button variant="success" type="submit">구매</Button>
            </Form>
          </>
        )}
      </Container>
    </div>
  );
};

export default ProductDetail;