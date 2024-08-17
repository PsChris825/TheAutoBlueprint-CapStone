import { createContext, useState, useEffect } from "react";
import { Navigate } from "react-router-dom";
import { refreshToken } from "../api/authApi";

const refreshTimer = 1000 * 60 * 14;

const AuthContext = createContext();

export { AuthContext };

export default function AuthProvider({ children }) {
  const [principal, setPrincipal] = useState();
  const [initialized, setInitialized] = useState(false);

  const refresh = () => {
    refreshToken()
      .then((response) => {
        const { user, token } = response;
        localStorage.setItem("jwt", token); 
        login(user);
      })
      .catch(logout)
      .finally(() => {
        setInitialized(true);
      });
  };

  useEffect(refresh, []);

  const login = (user) => {
    setPrincipal(user);
    setTimeout(refresh, refreshTimer);
  };

  const logout = () => {
    setPrincipal();
    localStorage.removeItem("jwt");
  };

  if (!initialized) {
    return <div>Loading...</div>;
  }

  return (
    <AuthContext.Provider value={{ principal, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}