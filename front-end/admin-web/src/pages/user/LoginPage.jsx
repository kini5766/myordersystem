import { useState } from 'react';
import { login } from "../../util/fetchUtil";
import { Container, Form, Button } from 'react-bootstrap';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async e => {
    e.preventDefault();
    setError("");

    if (email === "" || password === "") {
      setError("아이디와 비밀번호를 입력하세요.");
      return;
    }

    try {
      const res = await fetch(`${BACKEND_API_BASE_URL}/member-service/member/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ email, password })
      });
      if (!res.ok) throw new Error("로그인 실패!!");
      const data = await res.json();
      login(data);
    } catch {
      setError("아이디 또는 비밀번호가 틀렸습니다.");
    }
  };

  return (
    <div>
      <Container>
        <br /><hr />
        <h1>로그인</h1>
        <Form onSubmit={handleLogin}>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>아이디</Form.Label>
            <Form.Control
              type="email"
              placeholder="아이디"
              value={email}
              onChange={e => setEmail(e.target.value.trim())}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>비밀번호</Form.Label>
            <Form.Control
              type="password"
              placeholder="비밀번호"
              value={password}
              onChange={e => setPassword(e.target.value.trim())}
              required
            />
          </Form.Group>
          {error && <p>{error}</p>}
          <Button variant="primary" type="submit">
            로그인
          </Button>
        </Form>
      </Container>
    </div>
  );
};

export default LoginPage;