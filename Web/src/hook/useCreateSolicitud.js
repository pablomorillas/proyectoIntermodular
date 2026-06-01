import { useState } from "react";
import { createSolicitud } from "../services/Service";

export const useCreateSolicitud = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const addSolicitud = async (data) => {
    try {
      setLoading(true);
      setError(null);
      await createSolicitud(data);
      return true;
    } catch (err) {
      setError(err.message || "Error al crear la solicitud");
      return false;
    } finally {
      setLoading(false);
    }
  };

  return { addSolicitud, loading, error };
};
