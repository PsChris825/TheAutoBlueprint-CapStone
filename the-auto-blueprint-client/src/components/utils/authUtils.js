// utils/authUtils.js

export const getToken = () => localStorage.getItem('jwt');

export const tokenToUser = (token) => {
  if (!token) {
    throw new Error("No token provided");
  }

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
