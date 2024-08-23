const AUTH_URL = "http://localhost:8080/security";

// Helper function to get the token from localStorage
const getToken = () => localStorage.getItem('jwt');

// Helper function to parse token and save to localStorage
const tokenToUser = (token) => {
  localStorage.setItem("jwt", token);
  const segments = token.split(".");
  if (segments.length !== 3) {
    throw new Error("Invalid token");
  }
  const userString = atob(segments[1]);
  const user = JSON.parse(userString);
  return {
    ...user,
    authorities: user.authorities ? user.authorities.split(",") : []
  };
};

// Login function
export async function login(credentials) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credentials),
  };

  const response = await fetch(`${AUTH_URL}/authenticate`, init);

  if (response.ok) {
    const tokenResponse = await response.json();
    return tokenToUser(tokenResponse.jwt_token);
  } else {
    return Promise.reject("Unauthorized");
  }
}

// Refresh token function
export async function refreshToken() {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${getToken()}`
    }
  };

  const response = await fetch(`${AUTH_URL}/refresh`, init);

  if (response.ok) {
    const tokenResponse = await response.json();
    const user = tokenToUser(tokenResponse.jwt_token);
    return { user, token: tokenResponse.jwt_token };
  } else {
    return Promise.reject("Unauthorized");
  }
}

// Sign-Up function
export async function signUp(userData) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userData),
  };

  const response = await fetch(`${AUTH_URL}/register`, init);

  if (response.ok) {
    const tokenResponse = await response.json();
    return tokenToUser(tokenResponse.jwt_token); // Automatically log in by saving the token
  } else {
    const error = await response.text();
    throw new Error(error || "Failed to create account");
  }
}



