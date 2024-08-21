import React from 'react';
import { NavLink } from 'react-router-dom';
import { AuthContext } from '../providers/AuthProvider';

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
        {/* Conditional Navigation Links */}
        {principal ? (
          <>
            <NavLink
              className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
              to="/profile"
            >
              Profile
            </NavLink>
          </>
        ) : (
          <NavLink
            className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
            to="/login"
          >
            Login
          </NavLink>
        )}

        {/* Common Navigation Links */}
        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
          to="/modification-list"
        >
          View Current Modification Plans
        </NavLink>

        <NavLink
          className="flex items-center opacity-75 hover:opacity-100 py-4 pl-6 nav-item mt-6"
          to="/post-list"
        >
          Forum
        </NavLink>
      </nav>
    </aside>
  );
};

export default NavBar;
