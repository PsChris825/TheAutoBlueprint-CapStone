import React, { useState, useEffect, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { fetchUserModificationPlans, deleteModificationPlan } from "../../api/modificationPlanApi";
import { fetchCarById } from "../../api/carApi";
import { AuthContext } from "../../providers/AuthProvider";

const ModificationPlanList = () => {
  const { principal } = useContext(AuthContext);
  const [modificationPlans, setModificationPlans] = useState([]);
  const [carDetails, setCarDetails] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const loadModificationPlans = async () => {
      try {
        const plans = await fetchUserModificationPlans(principal.app_user_id);
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

    if (principal) {
      loadModificationPlans();
    }
  }, [principal]);

  const handleDelete = (id) => {
    if (window.confirm("Are you sure you want to delete this modification plan?")) {
      deleteModificationPlan(id)
        .then(() => setModificationPlans(modificationPlans.filter((plan) => plan.planId !== id)))
        .catch((error) => console.error("Error deleting modification plan:", error));
    }
  };

  return (
    <div style={{ maxWidth: '768px', margin: 'auto', padding: '1.5rem', backgroundColor: '#f3f4f6', borderRadius: '0.5rem', boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)' }}>
      <h2 style={{ fontSize: '1.5rem', fontWeight: 'bold', color: '#5894CD', marginBottom: '1.5rem' }}>My Blueprints </h2>
      <ul style={{ listStyleType: 'none', padding: 0 }}>
        {modificationPlans.map((plan) => (
          <li key={plan.planId} style={{ padding: '1rem', border: '1px solid #d1d5db', borderRadius: '0.5rem', backgroundColor: 'white', marginBottom: '1rem', boxShadow: '0 1px 2px rgba(0, 0, 0, 0.05)' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <div>
                <h3 style={{ fontSize: '1.125rem', fontWeight: '600', color: '#1f2937' }}>{plan.planName}</h3>
                <p style={{ fontSize: '0.875rem', color: '#4b5563' }}>{plan.planDescription}</p>
                <p style={{ fontSize: '0.875rem', color: '#4b5563' }}>
                  Car: {carDetails[plan.planId]?.make} {carDetails[plan.planId]?.model} {carDetails[plan.planId]?.year}
                </p>
                <p style={{ fontSize: '0.875rem', color: '#4b5563' }}>
                  Budget: ${plan.budget} - Total Cost: ${plan.totalCost} - Hours of Completion: {plan.planHoursOfCompletion} - Cost vs Budget: ${plan.costVersusBudget}
                </p>
              </div>
              <div style={{ display: 'flex', gap: '1rem' }}>
                <Link
                  to={`/modification-form/${plan.planId}`}
                  style={{ color: '#3b82f6', textDecoration: 'none' }}
                >
                  Edit
                </Link>
                <button
                  onClick={() => handleDelete(plan.planId)}
                  style={{ color: '#ef4444', background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  Delete
                </button>
                <button
                  onClick={() => navigate(`/modification-plan/${plan.planId}/details`)}
                  style={{ color: '#10b981', background: 'none', border: 'none', cursor: 'pointer' }}
                >
                  View
                </button>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ModificationPlanList;
