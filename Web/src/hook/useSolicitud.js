import { useRef, useEffect, useState } from "react";
import { getSolicitudById } from "../services/Service";

export const useSolicitud = (id) => {
  const fetched = useRef(false);
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!id) return;
    fetched.current = false;

    if (fetched.current) return;
    fetched.current = true;

    const fetchSolicitud = async () => {
      try {
        setLoading(true);
        setError(null);
        const res = await getSolicitudById(id);
        setData(res);
      } catch (err) {
        setError(err.message || "Error al cargar la solicitud");
      } finally {
        setLoading(false);
      }
    };

    fetchSolicitud();
  }, [id]);

  return { data, loading, error };
};
