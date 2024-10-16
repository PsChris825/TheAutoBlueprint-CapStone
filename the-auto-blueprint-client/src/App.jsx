import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import Header from './components/Header';
import LoginForm from './components/LoginForm';
import SignUpForm from './components/SignUpForm'; // Import the SignUpForm component
import CarForm from './components/car/carForm';
import CarList from './components/car/carList';
import Home from './components/Home';
import PartCategoryList from './components/part/partCategoryList';
import PartList from './components/part/partList';
import PartForm from './components/part/partForm';
import PlanPartList from './components/part/planPartList';
import PlanPartForm from './components/part/planPartForm';
import ModificationList from './components/modificationPlan/ModificationPlanList';
import ModificationForm from './components/modificationPlan/ModificationPlanForm';
import PostForm from './components/forum/postForm'; 
import PostList from './components/forum/postList'; 
import Profile from './components/profile'; 
import ModificationPlanDetails from './components/modificationPlan/modificationPlanDetails';
import AuthProvider from './providers/AuthProvider'; // Make sure this is imported

function App() {
  return (
    <AuthProvider> {/* Wrap the app with AuthProvider */}
      <BrowserRouter>
        <NavBar />
        <div className="w-full flex flex-col h-screen overflow-y-hidden">
          <Header />
          <div className="w-full overflow-x-hidden border-t flex flex-col">
            <main className="w-full flex-grow p-6">
              <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<LoginForm />} />
                <Route path="/signup" element={<SignUpForm />} /> 
                <Route path="/car-form/:id" element={<CarForm />} />
                <Route path="/car-form" element={<CarForm />} />
                <Route path="/car-list" element={<CarList />} />
                <Route path="/part-category" element={<PartCategoryList />} />
                <Route path="/part-category/:categoryId/parts" element={<PartList />} />
                <Route path="/part-category/:categoryId/part/:partId" element={<PartForm />} />
                <Route path="/part-category/:categoryId/part/new" element={<PartForm />} />
                <Route path="/plan-part-list/:planId" element={<PlanPartList />} /> 
                <Route path="/plan-part-form/:planId/:carId?" element={<PlanPartForm />} />
                <Route path="/plan-part-form/:planId/:carId/:planPartId?" element={<PlanPartForm />} />
                <Route path="/modification-list" element={<ModificationList />} />
                <Route path="/modification-form/:id" element={<ModificationForm />} />
                <Route path="/modification-form" element={<ModificationForm />} />
                <Route path="/post-list" element={<PostList />} /> 
                <Route path="/profile" element={<Profile />} />
                <Route path="/modification-plan/:planId/details" element={<ModificationPlanDetails />} />
              </Routes>
            </main>
          </div>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;