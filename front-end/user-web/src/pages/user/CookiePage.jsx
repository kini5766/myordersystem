import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from "../../util/fetchUtil";

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

const CookiePage = () => {
  const navigate = useNavigate();
  useEffect(() => {
    const cookieToBody = async () => {
      try {
        const res = await fetch(`${BACKEND_API_BASE_URL}/member-service/member/cookie/exchange`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          credentials: "include"
        });
        console.log(res);
        if (!res.ok) throw new Error("인증 실패");
        const data = await res.json();
        login(data);
      } catch (err) {
        console.error(err);
        alert("소셜 로그인 실패!");
        navigate("/login");
      }
    }
    cookieToBody();
  }, [navigate]);
  return (
    <div>
      <p>로그인 처리 중입니다...</p>
    </div>
  );
};

export default CookiePage;