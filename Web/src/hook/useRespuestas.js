import { useEffect, useState } from "react";
import { getRespuestas } from "../services/Service";

export const useRespuestas = () => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    let cancelled = false;

    const fetchRespuestas = async () => {
      try {
        const res = await getRespuestas();
        if (!cancelled) setData(res);
      } catch {
        if (!cancelled) setError("Error al cargar las respuestas");
      } finally {
        if (!cancelled) setLoading(false);
      }
    };

    fetchRespuestas();
    return () => { cancelled = true; };
  }, []);

  return { data, loading, error };
};
