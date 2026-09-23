import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Link } from 'react-router-dom'

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
            <Link to="/home" className='nav-link'>Home</Link>
            <Link to="/joinForm" className='nav-link'>회원가입</Link>
            <Link to="/loginForm" className='nav-link'>로그인</Link>
            <Link to="/boardList" className='nav-link'>게시판</Link>
            <Link to="/boardWriteForm" className='nav-link'>글쓰기</Link>
            <NavDropdown title="Link" id="navbarScrollingDropdown">
              <NavDropdown.Item href="#">장바구니</NavDropdown.Item>
              <NavDropdown.Item href="#">구매</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#">환불</NavDropdown.Item>
            </NavDropdown>
            <Nav.Link href="#" disabled>
              Link
            </Nav.Link>
          </Nav>
          <Form className="d-flex">
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
            />
            <Button variant="outline-success">Search</Button>
          </Form>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}