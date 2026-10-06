import { useEffect, useState } from 'react';
import { Button, Card, Container } from 'react-bootstrap';
import { Link, useParams } from 'react-router-dom';
import { toBoardTypeLabel } from '../../util/BoardType';
import { formatLocal } from '../../util/formatLocal';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const BoardDetail = (props) => {
  const propsParam = useParams();
  const boardNo = propsParam.board_no;
  const boardType = propsParam.board_type;
  const title = toBoardTypeLabel(boardType);

  const [board, setBoard] = useState({
    title: '',
    content: '',
    updatedDate: ''
  });
  useEffect(() => {
    fetch(`${BACKEND_API_BASE_URL}/board-service/board/detail/${boardNo}`)
      .then(res => res.json())
      .then(res => setBoard(res));
  }, []);
  return (
    <div>
      <Container>
        <br /><br />
        <h3>{title}</h3>
        <hr /><br />
        <Card>
          <Card.Body>
            <Card.Title>{board.title}</Card.Title>
            <Card.Text>
              <small className="text-muted">{formatLocal(board.updatedDate)}</small>
            </Card.Text>
            <Card.Text>{board.content}</Card.Text>
          </Card.Body>
        </Card>
        <br />
        <Button as={Link} variant="success" to={`/board/${boardType}`}>돌아가기</Button>
      </Container>
    </div>
  );
};

export default BoardDetail;