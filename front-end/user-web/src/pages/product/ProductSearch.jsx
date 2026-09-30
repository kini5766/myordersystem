import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import ProductItem from '../../common/components/ProductItem';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const ProductSearch = (props) => {
  const propsParam = useParams();
  const search = propsParam.search;

  const [productList, setProductList] = useState([]);
  useEffect(() => {
    fetch(`${BACKEND_API_BASE_URL}/product-service/product/search/${search}`, {
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
        <h3>제품 검색:{search}</h3>
        <br /><br />
        {productList !== null ? productList.map(product =>
          <ProductItem key={product.id} product={product} />
        ) : <>검색 결과가 없습니다.</>}
      </Container>
    </div>
  );
};

export default ProductSearch;