import { useEffect, useState } from "react";
import { getRespuestasByClienteId } from "../services/Service";

export const useMisRespuestas = (clienteId) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!clienteId) return;
    let cancelled = false;

    const fetchRespuestas = async () => {
      try {
        const res = await getRespuestasByClienteId(clienteId);
        if (!cancelled) setData(res);
      } catch {
        if (!cancelled) setError("Error al cargar tus respuestas");
      } finally {
        if (!cancelled) setLoading(false);
      }
    };

    fetchRespuestas();
    return () => { cancelled = true; };
  }, [clienteId]);

  return { data, loading, error };
};
