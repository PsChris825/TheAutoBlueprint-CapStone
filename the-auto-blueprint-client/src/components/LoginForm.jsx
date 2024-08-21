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
      auth.login(user);  // Set user in AuthContext
      navigate('/profile');  // Redirect to profile page
    } catch (error) {
      setErrors(['Invalid username/password']);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label htmlFor="username" className="block text-gray-700">Username:</label>
        <input
          type="text"
          name="username"
          id="username"
          value={credentials.username}
          onChange={handleChange}
          className="border p-2 w-full"
        />
      </div>
      <div>
        <label htmlFor="password" className="block text-gray-700">Password:</label>
        <input
          type="password"
          name="password"
          id="password"
          value={credentials.password}
          onChange={handleChange}
          className="border p-2 w-full"
        />
      </div>
      {errors.length > 0 && <Errors errors={errors} />}
      <button type="submit" className="px-4 py-2 bg-blue-500 text-white rounded">
        Login
      </button>
    </form>
  );
};

export default LoginForm;
