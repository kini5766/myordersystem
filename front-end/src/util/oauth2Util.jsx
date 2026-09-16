const BACKEND_API_OAUTH2_URL = import.meta.env.VITE_BACKEND_API_OAUTH2_URL;

export function fetchOauth2(provider) {
    window.location.href = `${BACKEND_API_OAUTH2_URL}/${provider}`;
}
