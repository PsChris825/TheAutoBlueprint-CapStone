import { createContext, useState, useEffect } from "react";
import { refreshToken } from "../api/authApi";

const refreshTimer = 1000 * 60 * 14;

const AuthContext = createContext();

export { AuthContext };

export default function AuthProvider({ children }) {
  const [principal, setPrincipal] = useState(null);
  const [initialized, setInitialized] = useState(false);

  const refresh = () => {
    refreshToken()
      .then((response) => {
        const { user, token } = response;
        setPrincipal(user);
        localStorage.setItem("jwt", token); 
        setTimeout(refresh, refreshTimer); // Schedule the next refresh
      })
      .catch(logout) // Logout if refresh fails
      .finally(() => {
        setInitialized(true);
      });
  };

  useEffect(() => {
    // Check if there's already a token
    const token = localStorage.getItem("jwt");
    if (token) {
      refresh(); // Refresh token if present
    } else {
      setInitialized(true); // Skip initialization if no token
    }
  }, []);

  const login = (user) => {
    setPrincipal(user);
    setTimeout(refresh, refreshTimer);
  };

  const logout = () => {
    setPrincipal(null);
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
