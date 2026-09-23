import React from 'react';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const BoardItem = (props) => {
  const { board_no, board_title, board_content, board_writer, board_regdate } = props.board;
  return (
    <div>
      <Card>
        <Card.Body>
          <Card.Title>글번호 : {board_no}</Card.Title>
          <Link to={"/board/" + board_no} className='btn btn-primary'>상세보기</Link>
          <Card.Title>글제목 : {board_title}</Card.Title>
          <Card.Title>글내용 : {board_content}</Card.Title>
          <Card.Title>작성자 : {board_writer}</Card.Title>
        </Card.Body>
      </Card>
    </div>
  );
};

export default BoardItem;