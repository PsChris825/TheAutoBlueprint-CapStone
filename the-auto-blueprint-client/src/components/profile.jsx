import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../providers/AuthProvider';
import ModificationPlanList from './modificationPlan/modificationPlanList';

const Profile = () => {
  const { principal, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login'); // Redirect to login page after logout
  };

  const handleAddModificationPlan = () => {
    navigate('/modification-form'); // Redirect to modification form
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Profile</h1>
      <div className="mb-4">
        <h2 className="text-xl font-semibold">Welcome, {principal ? principal.username : 'User'}</h2>
        <button 
          onClick={handleLogout} 
          className="mt-2 px-4 py-2 bg-red-500 text-white rounded"
        >
          Logout
        </button>
      </div>
      <div>
        <ModificationPlanList />
        <button 
          onClick={handleAddModificationPlan} 
          className="mt-4 px-4 py-2 bg-blue-500 text-white rounded"
        >
          Add Modification Plan
        </button>
      </div>
    </div>
  );
};

export default Profile;
