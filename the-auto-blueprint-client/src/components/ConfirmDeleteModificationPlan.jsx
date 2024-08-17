import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

const ConfirmDeleteModificationPlan = () => {
  const [modificationPlan, setModificationPlan] = useState({});

  const params = useParams();
  const id = params.id;

  const navigate = useNavigate();

  useEffect(() => {
    fetch(`http://localhost:8080/api/modification-plan/${id}`)
      .then((response) => response.json())
      .then((json) => setModificationPlan(json));
  }, [id]);

  const handleDelete = () => {
    fetch(`http://localhost:8080/api/modification-plan/${id}`, {
      method: "DELETE",
    }).then(() => navigate("/modification-plans"));
  };

  return (
    <div className="max-w-4xl">
      <div className="px-4 sm:px-0">
        <h3 className="text-base font-semibold leading-7 text-gray-900">
          {modificationPlan.name}
        </h3>
        <p className="mt-1 max-w-2xl text-sm leading-6 text-gray-500">
          Are you sure you want to delete this modification plan?
        </p>
      </div>
      <div className="mt-6 border-t border-gray-100">
        <dl className="divide-y divide-gray-100">
          <div className="px-4 py-4 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
            <dt className="text-sm font-semibold leading-6 text-gray-900">
              Name
            </dt>
            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
              {modificationPlan.name}
            </dd>
          </div>
          <div className="px-4 py-4 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
            <dt className="text-sm font-semibold leading-6 text-gray-900">
              Description
            </dt>
            <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
              {modificationPlan.description}
            </dd>
          </div>
          {/* Add other fields as necessary */}
        </dl>
      </div>
      <div className="mt-6 flex space-x-4">
        <button
          className="px-4 py-1 text-white font-light tracking-wider bg-red-900 rounded"
          onClick={handleDelete}
        >
          Yes, delete
        </button>
        <button
          className="px-4 py-1 text-white font-light tracking-wider bg-gray-500 rounded"
          onClick={() => navigate("/modification-plans")}
        >
          No, abort
        </button>
      </div>
    </div>
  );
};

export default ConfirmDeleteModificationPlan;