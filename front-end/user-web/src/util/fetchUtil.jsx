const BACKEND_API_BASE_URL = import.meta.env.VITE_BACKEND_API_BASE_URL;

export function login(data) {
  localStorage.setItem("accessToken", data.accessToken);
  localStorage.setItem("refreshToken", data.refreshToken);
  window.location.href = "/user";
}

export function logout() {
  const refreshToken = localStorage.getItem("refreshToken");
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
  
  fetch(`${BACKEND_API_BASE_URL}/member-service/member/logout`, {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ refreshToken })
  }).finally(() => {
    window.location.href = "/login";
  });
}

export async function refreshAccessToken() {
  const refreshToken = localStorage.getItem("refreshToken");
  if (!refreshToken) throw new Error("refreshToken 없습니다.");
  const response = await fetch(`${import.meta.env.VITE_BACKEND_API_BASE_URL}/member-service/member/refresh`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ refreshToken }),
  });

  if (!response.ok) throw new Error("AccessToken 갱신 실패");

  const data = await response.json();
  localStorage.setItem("accessToken", data.accessToken);
  localStorage.setItem("refreshToken", data.refreshToken);
  
  return data.accessToken;
}

export async function fetchWithAccess(url, options = {}) {
  let accessToken = localStorage.getItem("accessToken");

  if (!options.headers) options.headers = {};
  options.headers["Authorization"] = `Bearer ${accessToken}`;
  let response = await fetch(url, options);
  if (response.status === 401) {
    try {
      accessToken = await refreshAccessToken();
      options.headers["Authorization"] = `Bearer ${accessToken}`;
      response = await fetch(url, options);
    } catch (err) {
      console.log(err);
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      window.location.href = "/login";
    }
    console.log("refreshToken 회전 성공")
  }
  if (!response.ok) {
    throw new Error(`HTTP 오류 : ${response.status}`);
  }

  return response;
}

export function hasAccess() {
  return !!localStorage.getItem("accessToken");
}

export function isMine(memberId) {
  const token = localStorage.getItem("accessToken");
  if (!token) return false;
  try {
    const payload = JSON.parse(atob(token.split(".")[1]));
    return String(payload.sub) === String(memberId);
  } catch {
    return false;
  }
}