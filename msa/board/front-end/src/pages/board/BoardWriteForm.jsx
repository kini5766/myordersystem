import React, { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const BoardWriteForm = () => {
  const navigate = useNavigate();

  const [board, setBoard] = useState({
    board_title: '',
    board_content: '',
    board_writer: ''
  });
  const changeValue = e => setBoard({
    ...board,
    [e.target.name]: e.target.value
  });
  const submitBoard = e => {
    e.preventDefault();

    fetch("http://localhost:8081/api/v1/board", {
      method: "POST",
      headers: {
        "Content-Type": "application/json;charset=utf8"
      },
      body: JSON.stringify(board)
    }).then(res => {
      if (res.status === 201) {
        return res.json();
      } else {
        return null;
      }
    }).then(res => {
      if (res != null) {
        navigate('/boardList');
      } else {
        alert('게시글 작성에 실패했습니다!!');
      }
    }).catch(error => {
      console.log("실패", error);
    });
  };
  return (
    <div>
      <Container>
        <br />
        <h3>글쓰기</h3>
        <Form onSubmit={submitBoard}>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>글제목</Form.Label>
            <Form.Control type="text" placeholder="Enter title" name="board_title" onChange={changeValue} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>글내용</Form.Label>
            <Form.Control type="text" placeholder="Enter content" name="board_content" onChange={changeValue} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>작성자</Form.Label>
            <Form.Control type="text" placeholder="Enter writer" name="board_writer" onChange={changeValue} />
          </Form.Group>

          <Button variant="primary" type="submit">
            작성완료
          </Button>
        </Form>
      </Container>
    </div>
  );
};

export default BoardWriteForm;
