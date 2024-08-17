import { useState, useContext } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import "./App.css";
import NavBar from "./components/NavBar";
import Header from "./components/Header";
import ModificationPlanList from "./components/ModificationPlanList.jsx";
import ModificationPlanForm from "./components/ModificationPlanForm.jsx";
import ConfirmDeleteModificationPlan from "./components/ConfirmDeleteModificationPlan";
import LoginForm from "./components/LoginForm";

import { AuthContext } from "./providers/AuthProvider.jsx";

function App() {
  const { considerRedirect } = useContext(AuthContext);
  const [user, setUser] = useState(null);

  const handleLoginSuccess = (user) => {
    setUser(user);
    localStorage.setItem('jwt', user.token); 
  };

  return (
    <BrowserRouter>
      <NavBar />
      <div className="w-full flex flex-col h-screen overflow-y-hidden">
        <Header />
        <div className="w-full overflow-x-hidden border-t flex flex-col">
          <main className="w-full flex-grow p-6">
            <Routes>
              <Route
                path="/login"
                element={<LoginForm onLoginSuccess={handleLoginSuccess} />}
              />
              <Route
                path="/"
                element={
                  user ? (
                    <ModificationPlanList />
                  ) : (
                    <Navigate to="/login" replace />
                  )
                }
              />
              <Route
                path="/modification-plan-form"
                element={
                  user ? (
                    <ModificationPlanForm />
                  ) : (
                    <Navigate to="/login" replace />
                  )
                }
              />
              <Route
                path="/modification-plans/delete/:id"
                element={
                  user ? (
                    <ConfirmDeleteModificationPlan />
                  ) : (
                    <Navigate to="/login" replace />
                  )
                }
              />
              <Route
                path="/form"
                element={
                  user ? (
                    <ModificationPlanForm />
                  ) : (
                    <Navigate to="/login" replace />
                  )
                }
              />
            </Routes>
          </main>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;