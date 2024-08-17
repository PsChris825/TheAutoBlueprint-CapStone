const modificationPlanUrl = "http://localhost:8080/api/modification-plan";

export async function getModificationPlanById(id) {
  try {
    const response = await fetch(`${modificationPlanUrl}/${id}`, {
      headers: {
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("jwt")}`
      }
    });

    if (response.ok) {
      return response.json();
    } else {
      throw new Error("Something went wrong.");
    }
  } catch (error) {
    return Promise.reject(error.message);
  }
}

export async function saveModificationPlan(modificationPlan) {
  let verb = "POST";
  let requestUrl = modificationPlanUrl;

  if (modificationPlan.planId) {
    verb = "PUT";
    requestUrl += `/${modificationPlan.planId}`;
  }

  try {
    const response = await fetch(requestUrl, {
      method: verb,
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("jwt")}`
      },
      body: JSON.stringify(modificationPlan),
    });

    if (response.status === 201 || response.status === 200) {
      return response.json();
    } else if (response.status === 204) {
      return;
    } else {
      const errors = await response.json();
      throw new Error(errors);
    }
  } catch (error) {
    return Promise.reject(error.message);
  }
}