import React, { useEffect, useState } from 'react';
import { Container } from 'react-bootstrap';
import BoardItem from '../../common/components/BoardItem';

const BoardList = () => {
  const [boardList, setBoardList] = useState([]);
  useEffect(() => {
    fetch("http://localhost:8081/api/v1/boardList", {
      method: "GET"
    }).then(res => res.json())
      .then(res => {
        setBoardList(res);
      });
  }, []);
  return (
    <div>
      <Container>
        <br /><hr />
        <h3>BoardList</h3>
        <br /><br />
        {boardList.map(board =>
          <BoardItem key={board.board_no} board={board} />
        )}
      </Container>
    </div>
  );
};

export default BoardList;