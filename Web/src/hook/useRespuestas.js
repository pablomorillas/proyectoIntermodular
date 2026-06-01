import { useRef, useEffect, useState } from "react";
import { getRespuestas } from "../services/Service";

export const useRespuestas = () => {
  const fetched = useRef(false);
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (fetched.current) return;
    fetched.current = true;

    const fetchRespuestas = async () => {
      try {
        const res = await getRespuestas();
        setData(res);
      } catch {
        setError("Error al cargar las respuestas");
      } finally {
        setLoading(false);
      }
    };

    fetchRespuestas();
  }, []);

  return { data, loading, error };
};
