import { useState } from "react";
import { loginCliente } from "../services/Service";

export const useLogin = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const login = async (email, password) => {
    try {
      setLoading(true);
      setError(null);
      const data = await loginCliente({ email, password });
      return data;
    } catch (err) {
      setError(err.message || "Error al iniciar sesion");
      return null;
    } finally {
      setLoading(false);
    }
  };

  return { login, loading, error };
};
