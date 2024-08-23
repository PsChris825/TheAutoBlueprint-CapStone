import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { AuthContext } from '../providers/AuthProvider';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faCar, faComments, faSignInAlt, faUserPlus } from '@fortawesome/free-solid-svg-icons';
import logo from '../Images/The_Auto_Blueprint.png'; 

const NavBar = () => {
  const { principal, logout } = React.useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <aside className="h-screen w-64 hidden sm:block shadow-lg" style={{ backgroundColor: '#7AA6D3' }}>
      <div className="p-6 flex items-center justify-center">
        <NavLink to="/">
          <img
            src={logo}
            alt="Logo"
            className="w-48 h-auto"
          />
        </NavLink>
      </div>
      <nav className="text-white text-base font-medium pt-3 space-y-4">
        {principal ? (
          <>
            <NavLink
              className="flex items-center hover:bg-blue-800 py-4 pl-6 transition-all duration-200 ease-in-out"
              to="/profile"
            >
              <FontAwesomeIcon icon={faUser} className="w-6 h-6 mr-3" />
              Profile
            </NavLink>
            <button
              onClick={handleLogout}
              className="flex items-center hover:bg-blue-800 py-4 pl-6 transition-all duration-200 ease-in-out w-full text-left"
            >
              <FontAwesomeIcon icon={faSignInAlt} className="w-6 h-6 mr-3" />
              Logout
            </button>
          </>
        ) : (
          <>
            <NavLink
              className="flex items-center hover:bg-blue-800 py-4 pl-6 transition-all duration-200 ease-in-out"
              to="/login"
            >
              <FontAwesomeIcon icon={faSignInAlt} className="w-6 h-6 mr-3" />
              Login
            </NavLink>
            <NavLink
              className="flex items-center hover:bg-blue-800 py-4 pl-6 transition-all duration-200 ease-in-out"
              to="/signup"
            >
              <FontAwesomeIcon icon={faUserPlus} className="w-6 h-6 mr-3" />
              Register
            </NavLink>
          </>
        )}

        <NavLink
          className="flex items-center hover:bg-blue-800 py-4 pl-6 transition-all duration-200 ease-in-out"
          to="/modification-list"
        >
          <FontAwesomeIcon icon={faCar} className="w-6 h-6 mr-3" />
          Your Blueprints
        </NavLink>

        <NavLink
          className="flex items-center hover:bg-blue-800 py-4 pl-6 transition-all duration-200 ease-in-out"
          to="/post-list"
        >
          <FontAwesomeIcon icon={faComments} className="w-6 h-6 mr-3" />
          Forum
        </NavLink>
      </nav>
    </aside>
  );
};

export default NavBar;
