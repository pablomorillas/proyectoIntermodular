import { useState } from "react";
import { createCliente } from "../services/Service";

export const useRegister = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const register = async (data) => {
    try {
      setLoading(true);
      setError(null);
      await createCliente(data);
      return true;
    } catch (err) {
      setError(err.message || "Error al registrar el usuario");
      return false;
    } finally {
      setLoading(false);
    }
  };

  return { register, loading, error };
};
