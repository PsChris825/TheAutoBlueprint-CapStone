import React, { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../providers/AuthProvider";
import mustangImage from "../Images/Safari_RS_Porsche.jpeg"; 
import bmwImage from "../Images/E30_M3.jpeg";
import gtrImage from "../Images/370z.jpeg";

const Home = () => {
  const { principal } = useContext(AuthContext); // Access authentication context
  const navigate = useNavigate();

  const handleForumNavigation = () => {
    if (principal) {
      navigate("/post-list"); // Redirect to forum if user is logged in
    } else {
      navigate("/login"); // Redirect to login if user is not logged in
    }
  };

  const handleGetStarted = () => {
    if (principal) {
      navigate("/profile"); // Redirect to profile if user is logged in
    } else {
      navigate("/login"); // Redirect to login if user is not logged in
    }
  };

  return (
    <div className="flex flex-col items-center space-y-12">
      {/* Call to Action Banner */}
      <div className="w-full bg-blue-600 text-white text-center py-12">
        <h2 className="text-3xl font-bold mb-4">Welcome to The Auto Blueprint</h2>
        <p className="text-lg mb-6">Plan, track, and share your vehicle modifications with our community of car enthusiasts.</p>
        <button onClick={handleGetStarted} className="bg-white text-blue-600 px-6 py-3 font-semibold rounded hover:bg-gray-100 transition duration-300">
          Get Started Today
        </button>
      </div>

      {/* Top Build of the Week Section */}
      <div className="w-full max-w-4xl">
        <h2 className="text-2xl font-bold text-center mb-4">Top Builds of the Week</h2>
        <p className="text-center text-gray-600 mb-8">
          We feature the best builds from our community each week. Share your project in the{" "}
          <button onClick={handleForumNavigation} className="text-blue-600 underline">
            forum
          </button>{" "}
          for a chance to be highlighted here!
        </p>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {/* Example Car 1 */}
          <div className="bg-white shadow-lg rounded-lg overflow-hidden">
            <img src={mustangImage} alt="2020 Ford Mustang GT" className="w-full h-48 object-cover" />
            <div className="p-4">
              <h3 className="text-xl font-semibold mb-2">Rally ready 911 </h3>
              <p className="text-gray-600">Amazing safari rally build on this classic Porsche.</p>
            </div>
          </div>

          {/* Example Car 2 */}
          <div className="bg-white shadow-lg rounded-lg overflow-hidden">
            <img src={bmwImage} alt="2021 BMW M4" className="w-full h-48 object-cover" />
            <div className="p-4">
              <h3 className="text-xl font-semibold mb-2">E30 M3</h3>
              <p className="text-gray-600">400hp E30 monster, ready to tear up the streets.</p>
            </div>
          </div>

          {/* Example Car 3 */}
          <div className="bg-white shadow-lg rounded-lg overflow-hidden">
            <img src={gtrImage} alt="2019 Nissan GT-R" className="w-full h-48 object-cover" />
            <div className="p-4">
              <h3 className="text-xl font-semibold mb-2">Drift Build 370z</h3>
              <p className="text-gray-600">Check out this awesome drift ready 370z.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
