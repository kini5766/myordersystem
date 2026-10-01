import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from 'react-router-dom'
import { hasAccess, logout } from "../util/fetchUtil";

const Header = () => {
  return NavScroll();
}

export default Header;

function NavScroll() {
  return (
    <Navbar bg="dark" variant="dark">
      <Container fluid>
        <Navbar.Brand href="#">관리자 사이트</Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: '100px' }}
            navbarScroll
          >
            <Link to="/board" className='nav-link'>게시판</Link>
            {hasAccess() ? (
              <>
                <Link to="/user" className='nav-link'>내 정보</Link>
                <Link onClick={logout} className='nav-link'>로그아웃</Link>
              </>
            ) : (
              <>
                <Link to="/login" className='nav-link'>로그인</Link>
              </>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}