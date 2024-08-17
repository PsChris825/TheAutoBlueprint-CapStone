import { useContext } from "react";
import { NavLink } from "react-router-dom";
import { AuthContext } from "../providers/AuthProvider";

const NavBar = () => {
  const { principal, logout } = useContext(AuthContext);

  return (
    <aside class="relative bg-sidebar h-screen w-64 hidden sm:block shadow-xl">
      <div className="p-6">
        <NavLink to="/">
          <img
            src="./board-game-logo-free-vector.jpg"
            alt="Logo"
            className="w-56 h-auto"
          />
        </NavLink>
      </div>
      <nav className="text-blue-900 text-base font-semibold pt-3">
        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          to="/list"
        >
          View Games
        </NavLink>
        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          to="/form"
        >
          Add a Game
        </NavLink>
      </nav>
    </aside>
  );
};

export default NavBar;
