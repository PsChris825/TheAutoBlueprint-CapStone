// src/api/modificationPlanApi.js

export const getModificationPlans = async () => {
    const response = await fetch('http://localhost:8080/api/modification-plan', {
      headers: {
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("jwt")}`
      }
    });
  
    if (response.ok) {
      return await response.json();
    } else {
      throw new Error("Failed to fetch modification plans.");
    }
  };
  
  export const saveModificationPlan = async (modificationPlan) => {
    const response = await fetch('http://localhost:8080/api/modification-plan', {
      method: 'POST',
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("jwt")}`
      },
      body: JSON.stringify(modificationPlan)
    });
  
    if (response.ok) {
      return await response.json();
    } else {
      throw new Error("Failed to save modification plan.");
    }
  };