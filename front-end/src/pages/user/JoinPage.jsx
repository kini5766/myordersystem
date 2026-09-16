import { useEffect, useState } from 'react';
import { fetchOauth2 } from "../../util/oauth2Util";
import { useNavigate } from 'react-router-dom';
import { Container, Form, Button } from 'react-bootstrap';
import './LoginPage.css';
import naverLoginImg from '../../assets/NAVER_login_Dark_KR_green_narrow_H48.png';

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const JoinPage = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [isEmailValid, setIsEmailValid] = useState(null);
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [error, setError] = useState("");

  const checkEmail = async () => {
    if (email < 1) {
      setIsEmailValid(null);
      return;
    }
    try {
      const res = await fetch(`${BACKEND_API_BASE_URL}/member-service/member/exists/${email}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
        credentials: "include"
      });
      const exist = await res.json();
      setIsEmailValid(!exist);
    } catch {
      setIsEmailValid(null);
    }
  };

  useEffect(() => {
    setIsEmailValid(null);
  }, [email])

  const handleSignUp = async e => {
    e.preventDefault();
    setError("");
    if (email.length < 4 ||
      password.length < 4 ||
      name.trim() === "" ||
      email.trim() === ""
    ) {
      setError("필수 항목에 값을 입력해주세요.");
      return;
    }
    try {
      const res = await fetch(`${BACKEND_API_BASE_URL}/member-service/member/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify({ email, password, name })
      });
      if (!res.ok) throw new Error("회원가입 실패");
      navigate("/login");
    } catch (e) {
      console.error(e);
      setError("회원가입 중 오류가 발생했습니다!!");
    }
  };

  const handleSocialLogin = provider => fetchOauth2(provider);

  return (
    <div>
      <Container>
        <br /><hr />
        <h1>회원가입</h1>

        <Form onSubmit={handleSignUp}>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>이메일</Form.Label>
            <Form.Control
              type="email"
              placeholder="아이디(이메일)"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
            />
            {" "}
            <Form.Control
              type="button"
              disabled={isEmailValid === true}
              value="이메일 중복 확인"
              onClick={checkEmail}
            />
            {isEmailValid === false && (
              <p>이미 사용 중인 아이디입니다.</p>
            )}
            {isEmailValid === true && (
              <p>사용 가능한 아이디입니다.</p>
            )}
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>비밀번호</Form.Label>
            <Form.Control
              type="password"
              placeholder="비밀번호 (4자 이상)"
              value={password}
              onChange={e => setPassword(e.target.value)}
              required
              minLength={4}
            />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>닉네임</Form.Label>
            <Form.Control
              type="text"
              placeholder="닉네임"
              value={name}
              onChange={e => setName(e.target.value)}
              required
            />
          </Form.Group>
          {error && <p>{error}</p>}
          <Button variant="primary" type="submit" disabled={isEmailValid !== true}>
            회원가입
          </Button>
        </Form>

        <br />
        <br />
        <p>- OR -</p>

        {/* 소셜 로그인 버튼 */}
        <div>
          {/* https://developers.google.com/identity/branding-guidelines?hl=ko&utm_source */}
          <button className="gsi-material-button" onClick={() => handleSocialLogin("google")}>
            <div className="gsi-material-button-state"></div>
            <div className="gsi-material-button-content-wrapper">
              <div className="gsi-material-button-icon">
                <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" style={{ display: 'block' }}>
                  <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"></path>
                  <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"></path>
                  <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"></path>
                  <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"></path>
                  <path fill="none" d="M0 0h48v48H0z"></path>
                </svg>
              </div>
              <span className="gsi-material-button-contents">Sign up with Google</span>
              <span style={{ display: 'none' }}>Sign up with Google</span>
            </div>
          </button>
          <br />
          <br />
          <button className="naver-login-btn" onClick={() => handleSocialLogin("naver")}>
            {/* https://developers.naver.com/docs/login/bi/bi.md */}
            <img
              src={naverLoginImg}
              alt="네이버 로그인"
            />
          </button>
        </div>
      </Container>
    </div>
  );
};

export default JoinPage;