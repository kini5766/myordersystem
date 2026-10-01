import { useState } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { fetchWithAccess } from '../../util/fetchUtil'
import { BOARD_TYPE_LABEL } from '../../util/BoardType'

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const BoardWriteForm = () => {
  const navigate = useNavigate();

  const [board, setBoard] = useState({
    boardType: 'NOTICE',
    title: '',
    content: '',
    displayOrder: 0,
    displayStartDate: '',
    displayEndDate: '',
    active: true,
  });

  const changeValue = (e) => {
    const { name, value, type, checked } = e.target;

    setBoard({
      ...board,
      [name]: type === 'checkbox' ? !checked : value,
    });
  };

  const submitBoard = e => {
    e.preventDefault();

    fetchWithAccess(`${BACKEND_API_BASE_URL}/board-service/board/admin/create`, {
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
        navigate('/board');
      } else {
        alert('게시글 작성에 실패했습니다!!');
      }
    });
  };

  return (
    <div>
      <Container>
        <br />
        <br />
        <h3>글쓰기</h3>
        <hr />
        <br />
        
        <Form onSubmit={submitBoard}>

          {/* 게시판 유형 */}
          <Form.Group className="mb-3">
            <Form.Label>게시판 유형</Form.Label>
            <Form.Select
              name="boardType"
              value={board.boardType}
              onChange={changeValue}
            >
              {Object.entries(BOARD_TYPE_LABEL).map(([value, label]) => (
                <option key={value} value={value}>
                  {label}
                </option>
              ))}
            </Form.Select>
          </Form.Group>

          {/* 글제목 */}
          <Form.Group className="mb-3">
            <Form.Label>글제목</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter title"
              onChange={changeValue}
              name="title"
              value={board.title}
            />
          </Form.Group>

          {/* 글내용 */}
          <Form.Group className="mb-3">
            <Form.Label>글내용</Form.Label>
            <Form.Control
              as="textarea"
              rows={5}
              placeholder="Enter content"
              onChange={changeValue}
              name="content"
              value={board.content}
            />
          </Form.Group>

          {/* 노출 순서 */}
          <Form.Group className="mb-3">
            <Form.Label>노출 순서</Form.Label>
            <Form.Control
              type="number"
              onChange={changeValue}
              name="displayOrder"
              value={board.displayOrder}
            />
          </Form.Group>

          {/* 노출 시작일 */}
          <Form.Group className="mb-3">
            <Form.Label>노출 시작일</Form.Label>
            <Form.Control
              type="datetime-local"
              onChange={changeValue}
              name="displayStartDate"
              value={board.displayStartDate}
            />
          </Form.Group>

          {/* 노출 종료일 */}
          <Form.Group className="mb-3">
            <Form.Label>노출 종료일</Form.Label>
            <Form.Control
              type="datetime-local"
              onChange={changeValue}
              name="displayEndDate"
              value={board.displayEndDate}
            />
          </Form.Group>

          {/* 활성 여부 */}
          <Form.Group className="mb-3">
            <Form.Check
              type="checkbox"
              label="숨김 여부"
              name="active"
              checked={!board.active}
              onChange={changeValue}
            />
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
