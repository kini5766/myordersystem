import { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { fetchWithAccess } from "../../util/fetchUtil";

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const ProductWriteForm = () => {
  const navigate = useNavigate();

  const [product, setProduct] = useState({
    name: '',
    price: 0,
    stockQuantity: 0
  });
  const changeValue = e => setProduct({
    ...product,
    [e.target.name]: e.target.value
  });
  const submitProduct = e => {
    e.preventDefault();

    fetchWithAccess(`${BACKEND_API_BASE_URL}/product-service/product/create`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf8"
      },
      body: JSON.stringify(product)
    }).then(res => {
      if (res.status === 201) {
        return res.json();
      } else {
        return null;
      }
    }).then(res => {
      if (res != null) {
        navigate('/');
      } else {
        alert('제품 등록에 실패했습니다!!');
      }
    }).catch(error => {
      console.log("실패", error);
    });
  };
  return (
    <div>
      <Container>
        <br /><hr />
        <h3>제품 등록</h3>
        <Form onSubmit={submitProduct}>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>제품명</Form.Label>
            <Form.Control type="text" placeholder="Enter content" name="name" onChange={changeValue} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>가격</Form.Label>
            <Form.Control type="number" placeholder="Enter content" name="price" onChange={changeValue} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>재고</Form.Label>
            <Form.Control type="number" placeholder="Enter content" name="stockQuantity" onChange={changeValue} />
          </Form.Group>

          <Button variant="primary" type="submit">
            등록
          </Button>
        </Form>
      </Container>
    </div>
  );
};

export default ProductWriteForm;
