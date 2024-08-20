import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../api/authApi'; 
import { AuthContext } from '../providers/AuthProvider';
import Errors from './Errors';

const LoginForm = () => {
  const [credentials, setCredentials] = useState({ username: '', password: '' });
  const [errors, setErrors] = useState([]);
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleChange = (event) => {
    setCredentials({ ...credentials, [event.target.name]: event.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setErrors([]);

    if (!credentials.username || !credentials.password) {
      setErrors(['Username and password are required']);
      return;
    }

    try {
      const user = await login(credentials);
      auth.login(user);
      navigate('/car-list');
    } catch (error) {
      setErrors(['Invalid username/password']);
    }
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
