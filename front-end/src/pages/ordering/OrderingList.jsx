import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import OrderingItem from '../../common/components/OrderingItem';
import { fetchWithAccess } from "../../util/fetchUtil";

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const OrderingList = () => {
  const [orderingList, setOrderingList] = useState([]);
  useEffect(() => {
    fetchWithAccess(`${BACKEND_API_BASE_URL}/ordering-service/ordering/myList`, {
      method: "GET"
    }).then(res => res.json())
      .then(res => {
        setOrderingList(res);
      });
  }, []);
  return (
    <div>
      <Container>
        <br /><hr />
        <h3>주문 목록</h3>
        <br /><br />
        {orderingList !== null ? orderingList.map(ordering =>
          <OrderingItem key={ordering.id} ordering={ordering} />
        ) : <>주문이 없습니니다.</>}
      </Container>
    </div>
  );
};

export default OrderingList;