import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/authApi";
import Errors from "./Errors";
import { AuthContext } from "../providers/AuthProvider";

const LoginForm = () => {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const auth = useContext(AuthContext);
  const navigate = useNavigate();
  const [errors, setErrors] = useState([]);

  const handleChange = (event) => {
    setCredentials({
      ...credentials,
      [event.target.name]: event.target.value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (!credentials.username || !credentials.password) {
      setErrors(["Username and password are required"]);
      return;
    }

    login(credentials)
      .then((response) => {
        const { user, token } = response;
        localStorage.setItem("jwt", token); 
        auth.login(user);
        navigate('/list');
      })
      .catch(() => {
        setErrors(["Invalid username/password"]);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          name="username"
          value={credentials.username}
          onChange={handleChange}
        />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          name="password"
          value={credentials.password}
          onChange={handleChange}
        />
      </div>
      {errors.length > 0 && <Errors errors={errors} />}
      <button type="submit">Login</button>
    </form>
  );
};

export default LoginForm;