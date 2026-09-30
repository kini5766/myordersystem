import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
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
    fetchWithAccess(`${BACKEND_API_BASE_URL}/board-service/admin/list`, {
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
        <hr />
        <h3>BoardList</h3>
        <br />
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
