import React, { useState, useEffect } from "react";
import { fetchPartCategories } from "../../api/partCategoryApi";
import { Link } from "react-router-dom";

const PartCategoryList = () => {
  const [categories, setCategories] = useState([]);

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

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Part Categories</h1>
      <div>
        {categories.map((category) => (
          <div key={category.categoryId} className="mb-4">
            <Link
              to={`/part-category/${category.categoryId}/parts`}
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              {category.categoryName}
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PartCategoryList;
