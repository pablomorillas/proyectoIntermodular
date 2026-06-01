import { useRef, useEffect, useState } from "react";
import { getSolicitudesPublicas } from "../services/Service";

export const useSolicitudes = () => {
  const fetched = useRef(false);
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (fetched.current) return;
    fetched.current = true;

    const fetchSolicitudes = async () => {
      try {
        const res = await getSolicitudesPublicas();
        setData(res);
      } catch {
        setError("Error al cargar las solicitudes");
      } finally {
        setLoading(false);
      }
    };

    fetchSolicitudes();
  }, []);

  return { data, loading, error };
};
