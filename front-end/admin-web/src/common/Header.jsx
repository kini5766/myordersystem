import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
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
        <Navbar.Brand href="#">Navbar scroll</Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: '100px' }}
            navbarScroll
          >
            <Link to="/board" className='nav-link'>게시판</Link>
            <Link to="/board/write" className='nav-link'>글쓰기</Link>
            {hasAccess() ? (
              <NavDropdown title="My" id="navbarScrollingDropdown">
                <NavDropdown.Item href="/user">내 정보</NavDropdown.Item>
                <NavDropdown.Item onClick={logout}>로그아웃</NavDropdown.Item>
              </NavDropdown>
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