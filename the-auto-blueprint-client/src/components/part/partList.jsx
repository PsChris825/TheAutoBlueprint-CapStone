import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { fetchPartsByCategoryId, deletePart } from '../../api/partApi';

const PartList = () => {
  const { categoryId } = useParams();
  const [parts, setParts] = useState([]);

  useEffect(() => {
    const loadParts = async () => {
      try {
        const data = await fetchPartsByCategoryId(categoryId);
        setParts(data);
      } catch (error) {
        console.error("Failed to fetch parts:", error);
      }
    };

    loadParts();
  }, [categoryId]);

  const handleDelete = async (id) => {
    try {
      await deletePart(id);
      setParts(parts.filter(part => part.partId !== id));
    } catch (error) {
      console.error(`Failed to delete part with id ${id}:`, error);
    }
  };

  return (
    <div>
      <h1>Parts List</h1>
      <Link to={`/part-category/${categoryId}/part/new`} className="bg-green-500 text-white px-4 py-2 rounded mb-4">
        Add New Part
      </Link>
      <ul>
        {parts.map(part => (
          <li key={part.partId}>
            {part.partName} - {part.partNumber}
            <button onClick={() => handleDelete(part.partId)}>Delete</button>
            <Link to={`/part-category/${categoryId}/part/${part.partId}`}>Edit</Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PartList;
