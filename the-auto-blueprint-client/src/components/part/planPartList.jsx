import React, { useState, useEffect } from 'react';
import { fetchPlanParts, deletePlanPart } from '../../api/planPartApi';
import { Link } from 'react-router-dom';

const PlanPartList = () => {
  const [planParts, setPlanParts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadPlanParts = async () => {
      setLoading(true);
      setError('');
      try {
        const data = await fetchPlanParts();
        setPlanParts(data);
      } catch (error) {
        console.error('Failed to fetch plan parts:', error);
        setError('Failed to load plan parts.');
      } finally {
        setLoading(false);
      }
    };

    loadPlanParts();
  }, []);

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
    <div className="p-6">
      <h1 className="text-xl font-bold mb-4">Plan Parts List</h1>
      {loading && <p>Loading...</p>}
      {error && <p className="text-red-500">{error}</p>}
      <ul className="list-disc pl-5">
        {planParts.map(part => (
          <li key={part.planPartId} className="mb-2">
            <span>{part.partId} - ${part.price} - {part.timeToInstall} mins</span>
            <button
              className="ml-4 text-red-500"
              onClick={() => handleDelete(part.planPartId)}
            >
              Delete
            </button>
            <Link
              to={`/plan-part-form/${part.planPartId}`}
              className="ml-4 text-blue-500"
            >
              Edit
            </Link>
          </li>
        ))}
      </ul>
      <Link
        to="/plan-part-form"
        className="mt-4 inline-block px-4 py-2 bg-blue-500 text-white rounded"
      >
        Add New Plan Part
      </Link>
    </div>
  );
};

export default PlanPartList;
