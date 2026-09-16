export function login(data) {
  localStorage.setItem("id", data.memberId);
  localStorage.setItem("accessToken", data.accessToken);
  localStorage.setItem("refreshToken", data.refreshToken);
  window.location.href = "/user";
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
  localStorage.setItem("id", data.memberId);
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
      console.log(err)
      localStorage.removeItem("id");
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
  return localStorage.getItem("accessToken") !== null;
}

export function isMine(memberId) {
  console.log(`${memberId} : ${localStorage.getItem("id")}`)
  return hasAccess()
      && localStorage.getItem("id") == memberId;
}