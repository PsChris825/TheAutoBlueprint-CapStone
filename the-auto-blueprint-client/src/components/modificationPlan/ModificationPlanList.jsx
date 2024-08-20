import React, { useState, useEffect } from 'react';
import { getModificationPlans } from '../../api/modificationPlanApi';

const ModificationPlanList = () => {
  const [modificationPlans, setModificationPlans] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchModificationPlans = async () => {
      try {
        const data = await getModificationPlans();
        setModificationPlans(data);
      } catch (error) {
        setError(error.message);
      }
    };

    fetchModificationPlans();
  }, []);

  if (error) {
    return <div>Error: {error}</div>;
  }

  return (
    <div>
      <h1>Modification Plans</h1>
      <ul>
        {modificationPlans.map(plan => (
          <li key={plan.planId}>
            <h2>{plan.name}</h2>
            <p>{plan.description}</p>
            {/* Add other fields as necessary */}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ModificationPlanList;