import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { fetchModificationPlanById } from '../../api/modificationPlanApi';
import { fetchPlanPartsByPlanId, deletePlanPart } from '../../api/planPartApi';
import { fetchCarById } from '../../api/carApi'; // Import the function to fetch car details

const ModificationPlanDetails = () => {
    const { planId } = useParams();
    const navigate = useNavigate();
    const [modificationPlan, setModificationPlan] = useState(null);
    const [planParts, setPlanParts] = useState([]);
    const [car, setCar] = useState(null); // State for car details
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError('');
            try {
                const plan = await fetchModificationPlanById(planId);
                setModificationPlan(plan);

                if (plan.carId) {
                    const carDetails = await fetchCarById(plan.carId);
                    setCar(carDetails);
                }

                const parts = await fetchPlanPartsByPlanId(planId);
                setPlanParts(parts);
            } catch (error) {
                console.error("Error fetching modification plan details:", error);
                setError('Failed to load modification plan details.');
            } finally {
                setLoading(false);
            }
        };

        if (planId) {
            fetchData();
        }
    }, [planId]);

    const handleDeletePlanPart = async (partId) => {
        if (window.confirm('Are you sure you want to mark this plan part as completed?')) {
            try {
                await deletePlanPart(partId);
                setPlanParts(planParts.filter(part => part.planPartId !== partId));
            } catch (error) {
                console.error("Error deleting plan part:", error);
                setError('Failed to delete plan part.');
            }
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p className="text-red-500">{error}</p>;
    if (!modificationPlan) return <div>No modification plan found.</div>;

    const totalCost = planParts.reduce((acc, part) => acc + (part.price || 0), 0);
    const remainingBudget = (modificationPlan.budget || 0) - totalCost;

    return (
        <div className="p-6">
            <h1 className="text-xl font-bold mb-4">{modificationPlan.planName}</h1>
            <p>{modificationPlan.planDescription}</p>
            <div className="mb-4">
                <p><strong>Budget:</strong> ${modificationPlan.budget.toFixed(2)}</p>
                <p><strong>Total Cost of Parts:</strong> ${totalCost.toFixed(2)}</p>
                <p><strong>Remaining Budget:</strong> ${remainingBudget.toFixed(2)}</p>
            </div>
            {car && (
                <div className="mb-4">
                    <h2 className="text-lg font-semibold mt-4">Car Details</h2>
                    <p><strong>Make:</strong> {car.make}</p>
                    <p><strong>Model:</strong> {car.model}</p>
                    <p><strong>Year:</strong> {car.year}</p>
                </div>
            )}
            <h2 className="text-lg font-semibold mt-4">Plan Parts</h2>
            {planParts.length > 0 ? (
                <ul className="list-disc pl-5">
                    {planParts.map(part => (
                        <li key={part.planPartId} className="mb-2">
                            <div>
                                <span>
                                    {part.partName} - ${part.price.toFixed(2)} - {part.timeToInstall} hrs
                                </span>
                                <button
                                    className="ml-4 text-red-500"
                                    onClick={() => handleDeletePlanPart(part.planPartId)}
                                >
                                    Mark as Completed
                                </button>
                                <Link
                                    to={`/plan-part-form/${planId}/${modificationPlan.carId}/${part.planPartId}`}
                                    className="ml-4 text-blue-500"
                                >
                                    Edit
                                </Link>
                            </div>
                            <div>
                                {part.supplierUrl && (
                                    <p><a href={part.supplierUrl} target="_blank" rel="noopener noreferrer" className="text-blue-500">Supplier</a></p>
                                )}
                                {part.tutorialUrl && (
                                    <p><a href={part.tutorialUrl} target="_blank" rel="noopener noreferrer" className="text-blue-500">Tutorial</a></p>
                                )}
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No plan parts found.</p>
            )}
            <Link
                to={`/plan-part-form/${planId}/${modificationPlan.carId}`}
                className="mt-4 inline-block px-4 py-2 bg-blue-500 text-white rounded"
            >
                Add New Plan Part
            </Link>
        </div>
    );
};

export default ModificationPlanDetails;
