import { useEffect, useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { fetchWithAccess } from '../../util/fetchUtil'

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const BoardUpdateForm = () => {
  const { board_no } = useParams();
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

  useEffect(() => {
    fetchWithAccess(`${BACKEND_API_BASE_URL}/board-service/admin/detail/${board_no}`)
      .then(res => res.json())
      .then(res => setBoard(res));
  }, [board_no]);

  const changeValue = (e) => {
    const { name, value, type, checked } = e.target;

    setBoard({
      ...board,
      [name]: type === 'checkbox' ? !checked : value,
    });
  };

  const submitBoard = (e) => {
    e.preventDefault();

    fetchWithAccess(`${BACKEND_API_BASE_URL}/board-service/modifiy/${board_no}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf8',
      },
      body: JSON.stringify(board),
    }).then(res => {
        if (res.status === 200) {
          return res.json();
        } else {
          return null;
        }
      })
      .then(res => {
        if (res != null) {
          navigate(`/board`);
        } else {
          alert('게시글 수정에 실패했습니다!!');
        }
      });
  };

  return (
    <div>
      <Container>
        <br />
        <hr />
        <h3>글 수정</h3>
        <br />
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
              <option value="NOTICE">공지사항</option>
              <option value="EVENT">행사</option>
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
            수정
          </Button>
        </Form>
      </Container>
    </div>
  );
};

export default BoardUpdateForm;