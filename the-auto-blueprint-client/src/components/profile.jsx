import React, { useContext } from 'react'; 
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../providers/AuthProvider';
import ModificationPlanList from './modificationPlan/ModificationPlanList';

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
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-3xl font-bold text-[#5894CD] mb-6">Profile</h1>
      <div className="bg-white p-6 rounded-lg shadow-lg mb-6">
        <h2 className="text-2xl font-semibold text-[#5894CD]">
          {principal && principal.sub ? `Welcome, ${principal.sub}` : 'Welcome, User'}
        </h2>
        <button 
          onClick={handleLogout} 
          className="mt-4 px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600 transition duration-200"
        >
          Logout
        </button>
      </div>
      <div className="bg-white p-6 rounded-lg shadow-lg">
        <ModificationPlanList />
        <button 
          onClick={handleAddModificationPlan} 
          className="mt-4 px-4 py-2 bg-blue-600 text-white rounded shadow-lg hover:bg-blue-800 transition duration-200"
        >
          Add New Blueprint
        </button>
      </div>
    </div>
  );
};

export default Profile;
