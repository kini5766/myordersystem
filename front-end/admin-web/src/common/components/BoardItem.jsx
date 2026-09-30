import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { fetchWithAccess } from '../../util/fetchUtil'

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const BoardItem = (props) => {
  const {
    boardNo,
    boardType,
    title,
    displayOrder,
    displayStartDate,
    displayEndDate,
    active,
  } = props.board;

  const deleteBoard = () => {
    fetchWithAccess(`${BACKEND_API_BASE_URL}/board-service/admin/delete/${boardNo}`, {
      method: "DELETE",
    })
      .then((res) => res.text())
      .then((res) => {
        console.log(res);
        if (res !== 'Success') {
          // 부모에게 삭제 완료를 알림
          props.onDelete(boardNo);
        } else {
          alert('삭제 실패');
        }
      });
  }

  return (
    <div>
      <Card>
        <Card.Body>
          <Card.Title>글번호 : {boardNo}</Card.Title>

          <Card.Text>게시판 유형 : {boardType}</Card.Text>
          <Card.Text>글제목 : {title}</Card.Text>
          <Card.Text>노출 순서 : {displayOrder}</Card.Text>
          <Card.Text>노출 시작일 : {displayStartDate}</Card.Text>
          <Card.Text>노출 종료일 : {displayEndDate}</Card.Text>
          <Card.Text>숨김 여부 : {active ? '기본' : '숨김'}</Card.Text>

          <Link to={`/board/${boardNo}`} className="btn btn-primary">글 수정</Link>
          <Button variant="warning" onClick={deleteBoard}>글 삭제</Button>
        </Card.Body>
      </Card>
    </div>
  );
};

export default BoardItem;
