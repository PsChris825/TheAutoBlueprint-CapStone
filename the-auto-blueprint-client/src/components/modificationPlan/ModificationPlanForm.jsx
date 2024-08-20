import React, { useState } from "react";
import { saveModificationPlan } from "../../api/modificationPlanApi";

const ModificationPlanForm = () => {
  const [modificationPlan, setModificationPlan] = useState({
    name: "",
    description: "",
    // Add other fields as necessary
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setModificationPlan((prevPlan) => ({
      ...prevPlan,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await saveModificationPlan(modificationPlan);
      // Handle success (e.g., redirect or show success message)
    } catch (error) {
      // Handle error (e.g., show error message)
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Name</label>
        <input
          type="text"
          name="name"
          value={modificationPlan.name}
          onChange={handleChange}
        />
      </div>
      <div>
        <label>Description</label>
        <input
          type="text"
          name="description"
          value={modificationPlan.description}
          onChange={handleChange}
        />
      </div>
      {/* Add other fields as necessary */}
      <button type="submit">Save</button>
    </form>
  );
};

export default ModificationPlanForm;