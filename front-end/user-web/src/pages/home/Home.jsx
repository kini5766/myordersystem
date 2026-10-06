import { useEffect, useState } from 'react';
import { Container, Card } from 'react-bootstrap';
import { Link, useParams } from 'react-router-dom'
import ProductItem from '../../common/components/ProductItem';
import { toBoardTypeLabel } from '../../util/BoardType';
import { formatLocal } from '../../util/formatLocal';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const Home = () => {
  return (
    <div>
      <Container>
        <br /><br />
        <h3>홈페이지</h3>
        <hr /><br />
        {BoardList("EVENT")}
        <br />
        {BoardList("NOTICE")}
        <br />
        {ProductList()}
      </Container>
    </div>
  );
};

export default Home;

const BoardList = (boardType) => {
  const title = toBoardTypeLabel(boardType);

  const [boardList, setBoardList] = useState([]);
  useEffect(() => {
    fetch(`${BACKEND_API_BASE_URL}/board-service/board/list/${boardType}`, {
      method: "GET"
    }).then(res => res.json())
      .then(res => {
        setBoardList(res);
      });
  }, [boardType]);

  return (
    <div>
      <Container>
        <h5>{title}</h5>
        {boardList.map(board =>
          <BoardItem key={board.boardNo} board={board} board_type={boardType} />
        )}
      </Container>
    </div>
  );
};

const BoardItem = (props) => {
  const boardType = props.board_type;

  const { boardNo, title, updatedDate } = props.board;
  return (
    <div>
      <Card as={Link} to={`/board/${boardType}/${boardNo}`} className="text-decoration-none text-body">
        <Card.Body>
          <Card.Title>{title}</Card.Title>
          <Card.Text>
            <small className="text-muted">{formatLocal(updatedDate)}</small>
          </Card.Text>
        </Card.Body>
      </Card>
    </div>
  );
};

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
        <h5>제품 목록</h5>
        {productList !== null ? productList.map(product =>
          <ProductItem key={product.id} product={product} />
        ) : <>제품이 없습니다.</>}
      </Container>
    </div>
  );
};