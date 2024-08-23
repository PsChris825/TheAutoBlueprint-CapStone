import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { fetchPlanPartsByPlanId, deletePlanPart } from '../../api/planPartApi';

const PlanPartList = () => {
  const { planId, carId } = useParams();
  const [planParts, setPlanParts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadPlanParts = async () => {
      setLoading(true);
      setError('');
      try {
        const data = await fetchPlanPartsByPlanId(planId);
        setPlanParts(data);
      } catch (error) {
        console.error('Failed to fetch plan parts:', error);
        setError('Failed to load plan parts.');
      } finally {
        setLoading(false);
      }
    };

    if (planId) {
      loadPlanParts();
    }
  }, [planId]);

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this plan part?')) {
      try {
        await deletePlanPart(id);
        setPlanParts(planParts.filter(part => part.planPartId !== id));
      } catch (error) {
        console.error(`Failed to delete plan part with id ${id}:`, error);
        setError(`Failed to delete plan part with id ${id}.`);
      }
    }
  };

  return (
    <div className="p-6 max-w-3xl mx-auto bg-white rounded-lg shadow-md">
      <h1 className="text-2xl font-bold mb-4 text-[#5894CD]">Plan Parts List</h1>
      {loading && <p className="text-gray-700">Loading...</p>}
      {error && <p className="text-red-500">{error}</p>}
      <ul className="list-disc pl-5 space-y-4">
        {planParts.length > 0 ? (
          planParts.map((part) => (
            <li key={part.planPartId} className="p-4 bg-gray-50 border rounded-lg shadow-sm flex justify-between items-center">
              <div className="text-gray-800">
                <span className="font-semibold">{part.partId} - ${part.price} - {part.timeToInstall} mins</span>
              </div>
              <div className="flex space-x-4">
                <button
                  className="text-red-500 hover:text-red-700 transition duration-200"
                  onClick={() => handleDelete(part.planPartId)}
                >
                  Delete
                </button>
                <Link
                  to={`/plan-part-form/${planId}/${carId}/${part.planPartId}`}
                  className="text-blue-500 hover:text-blue-700 transition duration-200"
                >
                  Edit
                </Link>
              </div>
            </li>
          ))
        ) : (
          <p className="text-gray-600">No plan parts found.</p>
        )}
      </ul>
      <Link
        to={`/plan-part-form/${planId}/${carId}`}
        className="mt-6 inline-block px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition duration-200"
      >
        Add New Plan Part
      </Link>
    </div>
  );
};

export default PlanPartList;
