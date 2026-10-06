import { Card } from 'react-bootstrap';
import { Link, useParams } from 'react-router-dom'
import { formatLocal } from '../../util/formatLocal';

const BoardItem = (props) => {
  const propsParam = useParams();
  const boardType = propsParam.board_type;

  const { boardNo, title, updatedDate } = props.board;
  return (
    <div>
      <Card as={Link} to={`/board/${boardType}/${boardNo}`} className="text-decoration-none text-body">
        <Card.Body>
          <Card.Title>{title}</Card.Title>
          <Card.Text>
            <small className="text-muted">{formatLocal(updatedDate)}</small>
          </Card.Text>
        </Card.Body>
      </Card>
    </div>
  );
};

export default BoardItem;
