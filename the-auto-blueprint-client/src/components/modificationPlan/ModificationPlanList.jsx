import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { fetchModificationPlans, deleteModificationPlan } from "../../api/modificationPlanApi";

const ModificationPlanList = () => {
  const [modificationPlans, setModificationPlans] = useState([]);

  useEffect(() => {
    fetchModificationPlans()
      .then((planList) => {
        console.log("Fetched Modification Plans:", planList); // Log the fetched data
        setModificationPlans(planList);
      })
      .catch((error) => console.error("Error fetching modification plans:", error));
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
                Budget: ${plan.budget} - Total Cost: ${plan.totalCost} - Hours of Completion: {plan.planHoursOfCompletion}
              </p>
            </div>
            <div>
              <Link to={`/modification-form/${plan.planId}`} className="text-blue-500 mr-4">Edit</Link>
              <button onClick={() => handleDelete(plan.planId)} className="text-red-500">Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ModificationPlanList;
