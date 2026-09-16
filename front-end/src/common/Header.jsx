import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'
import { hasAccess } from "../util/fetchUtil";

const Header = () => {
  const [searchText, setSearchText] = useState("");
  const changeSearchText = e => setSearchText(e.target.value);

  const navigate = useNavigate();
  const onSearch = e => {
    e.preventDefault();
    navigate(`/product/search/${searchText}`);
  }
  return (
    <Navbar bg="dark" variant="dark">
      <Container fluid>
        <Navbar.Brand href="/">Ordering System</Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: '100px' }}
            navbarScroll
          >
            <Link to="/" className='nav-link'>Home</Link>
            {hasAccess() ? (
              <NavDropdown title="My" id="navbarScrollingDropdown">
                <NavDropdown.Item href="/user">내 정보</NavDropdown.Item>
                <NavDropdown.Item href="/orderingList">주문 목록</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="/product">제품 등록</NavDropdown.Item>
                <NavDropdown.Item href="/myProductList">내 제품</NavDropdown.Item>
              </NavDropdown>
            ) : (
              <>
                <Link to="/join" className='nav-link'>회원가입</Link>
                <Link to="/login" className='nav-link'>로그인</Link>
              </>
            )}
          </Nav>
          <Form className="d-flex" onSubmit={onSearch}>
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
              onChange={changeSearchText}
            />
            <Button variant="outline-success" type="submit">Search</Button>
          </Form>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;