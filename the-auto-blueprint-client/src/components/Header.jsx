import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../providers/AuthProvider";

const Header = () => {
  const { principal } = useContext(AuthContext);
  const navigate = useNavigate();

  return (
    <header
      className="w-full py-4 px-6 flex items-center justify-between shadow-md"
      style={{ backgroundColor: '#5894CD' }} 
    >
      <div className="blueprint-text text-white text-xl font-bold">
        The Auto Blueprint
      </div>
    </header>
  );
};

export default Header;
