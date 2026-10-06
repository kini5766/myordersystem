import { useEffect, useState } from 'react';
import { Container, Button } from 'react-bootstrap';
import { Link } from 'react-router-dom'
import BoardItem from '../../common/components/BoardItem';
import { fetchWithAccess } from '../../util/fetchUtil';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const BoardList = () => {
  const [boardList, setBoardList] = useState([]);

  const handleDelete = (boardNo) => {
    setBoardList(prevList =>
      prevList.filter(board => board.boardNo !== boardNo)
    );
  };

  useEffect(() => {
    fetchWithAccess(`${BACKEND_API_BASE_URL}/board-service/admin/board/list`, {
      method: 'GET',
    })
      .then(res => res.json())
      .then(res => {
        setBoardList(res);
      });
  }, []);

  return (
    <div>
      <Container>
        <br />
        <br />
        <h3>공지/이벤트</h3>
        <Button variant="success" as={Link} to="/board/write">글쓰기</Button>
        <hr />
        <br />

        {boardList.map(board => (
          <BoardItem
            key={board.boardNo}
            board={board}
            onDelete={handleDelete}
          />
        ))}
      </Container>
    </div>
  );
};

export default BoardList;
