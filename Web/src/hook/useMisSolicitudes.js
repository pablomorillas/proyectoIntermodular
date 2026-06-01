import { useEffect, useState } from "react";
import { getSolicitudesByClienteId } from "../services/Service";

export const useMisSolicitudes = (clienteId) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!clienteId) return;
    let cancelled = false;

    const fetchSolicitudes = async () => {
      try {
        const res = await getSolicitudesByClienteId(clienteId);
        if (!cancelled) setData(res);
      } catch {
        if (!cancelled) setError("Error al cargar tus solicitudes");
      } finally {
        if (!cancelled) setLoading(false);
      }
    };

    fetchSolicitudes();
    return () => { cancelled = true; };
  }, [clienteId]);

  return { data, loading, error };
};
