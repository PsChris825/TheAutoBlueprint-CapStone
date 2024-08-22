import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { fetchModificationPlans, deleteModificationPlan } from "../../api/modificationPlanApi";
import { fetchCarById } from "../../api/carApi";

const ModificationPlanList = () => {
  const [modificationPlans, setModificationPlans] = useState([]);
  const [carDetails, setCarDetails] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const loadModificationPlans = async () => {
      try {
        const plans = await fetchModificationPlans();
        setModificationPlans(plans);

        const fetchCarDetails = async () => {
          const details = {};
          for (const plan of plans) {
            if (plan.carId) {
              try {
                const car = await fetchCarById(plan.carId);
                details[plan.planId] = car;
              } catch (error) {
                console.error(`Error fetching car details for plan ${plan.planId}:`, error);
              }
            }
          }
          setCarDetails(details);
        };

        fetchCarDetails();
      } catch (error) {
        console.error("Error fetching modification plans:", error);
      }
    };

    loadModificationPlans();
  }, []);

  const handleDelete = (id) => {
    if (window.confirm("Are you sure you want to delete this modification plan?")) {
      deleteModificationPlan(id)
        .then(() => setModificationPlans(modificationPlans.filter((plan) => plan.planId !== id)))
        .catch((error) => console.error("Error deleting modification plan:", error));
    }
  };

  return (
    <div className="max-w-3xl mx-auto p-6">
      <h2 className="text-xl font-semibold mb-4">Modification Plan List</h2>
      <ul>
        {modificationPlans.map((plan) => (
          <li key={plan.planId} className="mb-2 p-4 border rounded flex justify-between items-center">
            <div>
              <strong>{plan.planName}</strong>
              <p>{plan.planDescription}</p>
              <p>
                Car: {carDetails[plan.planId]?.make} {carDetails[plan.planId]?.model} {carDetails[plan.planId]?.year}
              </p>
              <p>
                Budget: ${plan.budget} - Total Cost: ${plan.totalCost} - Hours of Completion: {plan.planHoursOfCompletion} - Cost vs Budget: {plan.costVersusBudget}$
              </p>
            </div>
            <div>
              <Link to={`/modification-form/${plan.planId}`} className="text-blue-500 mr-4">Edit</Link>
              <button onClick={() => handleDelete(plan.planId)} className="text-red-500 mr-4">Delete</button>
              <button 
                onClick={() => navigate(`/modification-plan/${plan.planId}/details`)} 
                className="text-green-500"
              >
                View
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ModificationPlanList;
