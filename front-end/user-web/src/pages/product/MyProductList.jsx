import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import ProductItem from '../../common/components/ProductItem';
import { fetchWithAccess } from "../../util/fetchUtil";

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const MyProductList = () => {
  const [productList, setProductList] = useState([]);
  useEffect(() => {
    fetchWithAccess(`${BACKEND_API_BASE_URL}/product-service/product/myList`, {
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
        <h3>내가 등록한 제품</h3>
        <br /><br />
        {productList !== null ? productList.map(product =>
          <ProductItem key={product.id} product={product} />
        ) : <>제품이 없습니다.</>}
      </Container>
    </div>
  );
};

export default MyProductList;