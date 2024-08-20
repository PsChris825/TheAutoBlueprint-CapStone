import React, { useState, useEffect } from "react";
import { fetchPartCategories } from "../../../api/partCategoryApi";
import { fetchPartsByCategoryId } from "../../../api/partApi"; // Adjust if necessary

const PartCategoryPage = () => {
  const [categories, setCategories] = useState([]);
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);
  const [parts, setParts] = useState([]);

  useEffect(() => {
    const loadCategories = async () => {
      try {
        const data = await fetchPartCategories();
        setCategories(data);
      } catch (err) {
        console.error("Failed to fetch categories:", err);
      }
    };

    loadCategories();
  }, []);

  const handleCategoryChange = async (categoryId) => {
    setSelectedCategoryId(categoryId);
    try {
      const data = await fetchPartsByCategoryId(categoryId); // Implement this function if necessary
      setParts(data);
    } catch (err) {
      console.error("Failed to fetch parts:", err);
    }
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Part Categories</h1>
      <div>
        {categories.map((category) => (
          <div key={category.categoryId} className="mb-4">
            <button
              onClick={() => handleCategoryChange(category.categoryId)}
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              {category.categoryName}
            </button>
            {selectedCategoryId === category.categoryId && (
              <ul className="mt-2">
                {parts.map((part) => (
                  <li key={part.partId} className="border-b border-gray-300 py-2">
                    {part.partName}
                  </li>
                ))}
              </ul>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default PartCategoryPage;
