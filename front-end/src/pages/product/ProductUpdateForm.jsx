import { useEffect, useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { fetchWithAccess } from "../../util/fetchUtil";

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const ProductUpdateForm = () => {
  const propsParam = useParams();
  const productId = propsParam.productId;
  const navigate = useNavigate();
  const [product, setProduct] = useState({
    id: 0,
    name: '',
    price: '',
    stockQuantity: ''
  });
  useEffect(() => {
    fetch(`${BACKEND_API_BASE_URL}/product-service/product/detail/${productId}`)
      .then(res => res.json())
      .then(res => setProduct(res));
  }, []);
  const changeValue = e => setProduct({...product, [e.target.name]: e.target.value});
  const submitProduct = e => {
    e.preventDefault();

    fetchWithAccess(`${BACKEND_API_BASE_URL}/product-service/product/modifiy/${productId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json;charset=utf8"
      },
      body: JSON.stringify(product)
    }).then(res => {
      if (res.status == 200) {
        return res.json();
      } else {
        return null;
      }
    }).then(res => {
      if (res != null) {
        navigate("/product/" + productId);
      } else {
        alert("제품 수정에 실패했습니다!!");
      }
    });
  };
  return (
    <div>
      <Container>
        <br />
        <h3>제품 수정</h3>
        <Form onSubmit={submitProduct}>
          <Form.Group className='mb-3' controlId='formBasicEmail'>
            <Form.Label>제품명</Form.Label>
            <Form.Control
              type='text'
              placeholder='Enter title'
              onChange={changeValue}
              name='name'
              value={product.name}
            />
          </Form.Group>

          <Form.Group className='mb-3' controlId='formBasicEmail'>
            <Form.Label>가격</Form.Label>
            <Form.Control
              type='text'
              placeholder='Enter content'
              onChange={changeValue}
              name='price'
              value={product.price}
            />
          </Form.Group>
          
          <Form.Group className='mb-3' controlId='formBasicEmail'>
            <Form.Label>재고</Form.Label>
            <Form.Control
              type='text'
              placeholder='Enter writer'
              onChange={changeValue}
              name='stockQuantity'
              value={product.stockQuantity}
            />
          </Form.Group>

          <Button variant='primary' type='submit'>
            수정
          </Button>
        </Form>
      </Container>
    </div>
  );
};

export default ProductUpdateForm;