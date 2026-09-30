import { useEffect, useState } from "react";
import { Container } from 'react-bootstrap';
import { fetchWithAccess } from "../../util/fetchUtil";

const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

function UserPage() {
  const [userInfo, setUserInfo] = useState(null);
  useEffect(() => {
    const userInfo = async () => {
      try {
        const res = await fetchWithAccess(`${BACKEND_API_BASE_URL}/member-service/member/info`, {
          method: "GET",
          credentials: "include",
          headers: {
            "Content-Type": "application/json"
          }
        });
        if (!res.ok) throw new Error("유저 정보 불러오기 실패");
        const data = await res.json();
        setUserInfo(data);
      } catch (err) {
        console.error(err);
        // setError("유저 정보를 불러오지 못했습니다.");
      }
    };
    userInfo();
  }, []);
  
  return (
    <div>
      <Container>
        <br /><hr />
        <h3>내 정보</h3>
        <p>이메일: {userInfo?.email}</p>
        <p>닉네임: {userInfo?.name}</p>
      </Container>
    </div>
  )
}

export default UserPage;