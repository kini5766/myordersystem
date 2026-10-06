import { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import { toBoardTypeLabel } from '../../util/BoardType';
import BoardItem from '../../common/components/BoardItem';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const BoardList = () => {
  const propsParam = useParams();
  const boardType = propsParam.board_type;
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
        <br /><br />
        <h3>{title}</h3>
        <hr /><br />
        {boardList.map(board =>
          <BoardItem key={board.boardNo} board={board} />
        )}
      </Container>
    </div>
  );
};

export default BoardList;