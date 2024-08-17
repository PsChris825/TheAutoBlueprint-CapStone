const url = "http://localhost:8080/security";

export async function login(credentials) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credentials),
  };

  const response = await fetch(`${url}/authenticate`, init);

  if (response.status === 200) {
    const tokenResponse = await response.json();
    return tokenToUser(tokenResponse.jwt_token);
  } else {
    return Promise.reject("Unauthorized");
  }
}

export async function refreshToken() {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${localStorage.getItem("jwt")}`
    }
  };

  const response = await fetch(`${url}/refresh`, init);

  if (response.status === 200) {
    const tokenResponse = await response.json();
    const user = tokenToUser(tokenResponse.jwt_token);
    return { user, token: tokenResponse.jwt_token }; 
  } else {
    return Promise.reject("Unauthorized");
  }
}

const tokenToUser = (token) => {
  localStorage.setItem("jwt", token);
  const segments = token.split(".");
  const userString = atob(segments[1]);
  const user = JSON.parse(userString);
  return {
    ...user,
    authorities: user.authorities.split(",")
  };
}
