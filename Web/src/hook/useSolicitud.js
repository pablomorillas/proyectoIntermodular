import { useEffect, useState } from "react";
import { getSolicitudById } from "../services/Service";

export const useSolicitud = (id) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!id) return;
    let cancelled = false;

    const fetchSolicitud = async () => {
      try {
        if (!cancelled) {
          setLoading(true);
          setError(null);
        }
        const res = await getSolicitudById(id);
        if (!cancelled) setData(res);
      } catch (err) {
        if (!cancelled) setError(err.message || "Error al cargar la solicitud");
      } finally {
        if (!cancelled) setLoading(false);
      }
    };

    fetchSolicitud();
    return () => { cancelled = true; };
  }, [id]);

  return { data, loading, error };
};
