import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../providers/AuthProvider";

const DEFAULT_AVATAR_URL =
  "https://www.pngitem.com/pimgs/m/137-1370051_avatar-generic-avatar-hd-png-download.png";

const Header = () => {
  const { principal, logout } = useContext(AuthContext);

  const [open, setOpen] = useState(false);

  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    setOpen(false);
    navigate("/list");
  };

  return (
    <header className="w-full items-center bg-white py-2 px-6 hidden sm:flex">
      <div className="w-1/2"></div>
      <div className="relative w-1/2 flex justify-end">
        <button
          onClick={() => setOpen(!open)}
          className="realtive z-10 w-12 h-12 rounded-full overflow-hidden border-4 border-gray-400 hover:border-gray-300 focus:border-gray-300 focus:outline-none"
        >
          <img src={DEFAULT_AVATAR_URL} />
        </button>
        {open && (
          <div className="absolute w-32 bg-white rounded-lg shadow-lg py-2 mt-16">
            <div>{principal && principal.username}</div>
            <button
              type="button"
              onClick={handleLogout}
              className="block px-4 py-2 account-link hover:text-white"
            >
              Sign Out
            </button>
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
