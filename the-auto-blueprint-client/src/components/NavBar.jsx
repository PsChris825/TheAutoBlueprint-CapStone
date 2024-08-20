import React from "react";
import { NavLink } from "react-router-dom";
import { AuthContext } from "../providers/AuthProvider";

const NavBar = () => {
  const { principal, logout } = React.useContext(AuthContext);

  return (
    <aside className="relative bg-sidebar h-screen w-64 hidden sm:block shadow-xl">
      <div className="p-6">
        <NavLink to="/">
          <img
            src="./car-logo-free-vector.jpg"
            alt="Logo"
            className="w-56 h-auto"
          />
        </NavLink>
      </div>
      <nav className="text-blue-900 text-base font-semibold pt-3">
        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          to="/car-list"
        >
          View Cars
        </NavLink>
        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          to="/car-form"
        >
          Add a Car
        </NavLink>
        {principal ? (
          <button
            onClick={logout}
            className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          >
            Logout
          </button>
        ) : (
          <NavLink
            className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
            to="/login"
          >
            Login
          </NavLink>
        )}
        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          to="/part-category"
        >
          Categories
        </NavLink>
        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          to="/plan-part-list"
        >
          Plan Parts
        </NavLink>
      </nav>
    </aside>
  );
};

export default NavBar;
