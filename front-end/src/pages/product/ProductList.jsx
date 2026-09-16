import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import ProductItem from '../../common/components/ProductItem';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const ProductList = () => {
  const [productList, setProductList] = useState([]);
  useEffect(() => {
    fetch(`${BACKEND_API_BASE_URL}/product-service/product/list`, {
      method: "GET"
    }).then(res => res.json())
      .then(res => {
        setProductList(res);
      });
  }, []);
  return (
    <div>
      <Container>
        <br /><hr />
        <h3>제품 목록</h3>
        <br /><br />
        {productList !== null ? productList.map(product =>
          <ProductItem key={product.id} product={product} />
        ) : <>제품이 없습니다.</>}
      </Container>
    </div>
  );
};

export default ProductList;