import { useEffect, useState } from 'react';
import { Button, Card, Container } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';

const BoardDetail = (props) => {
  const propsParam = useParams();
  const board_no = propsParam.board_no;
  const navigate = useNavigate();
  const [board, setBoard] = useState({
    board_no: 0,
    board_title: '',
    board_content: '',
    board_writer: ''
  });
  useEffect(() => {
    fetch("http://localhost:8081/api/board/" + board_no)
      .then(res => res.json())
      .then(res => setBoard(res));
  }, []);
  const updateBoard = () => navigate('/updateForm/' + board_no);
  const deleteBoard = () => {
    fetch("http://localhost:8081/api/board/" + board_no, {
      method: "DELETE"
    }).then(res => res.text())
      .then(res => {
        if (res === 'OK') {
          navigate('/boardList');
        } else {
          alert('삭제 실패');
        }
      });
  };
  return (
    <div>
      <Container>
        <Card>
          <Card.Body>
            <Card.Title>글번호 : {board.board_no}</Card.Title>
            <Card.Title>글제목 : {board.board_title}</Card.Title>
            <Card.Title>글내용 : {board.board_content}</Card.Title>
            <Card.Title>작성자 : {board.board_writer}</Card.Title>
          </Card.Body>
        </Card>
        <br />
        <Button variant='warning' onClick={updateBoard}>수정</Button>
        {' '}
        <Button variant='success' onClick={deleteBoard}>삭제</Button>
      </Container>
    </div>
  );
};

export default BoardDetail;