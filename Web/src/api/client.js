const API_BASE_URL = "http://localhost:8080";

async function fetchJson(url, options = {}) {
  const res = await fetch(`${API_BASE_URL}${url}`, {
    headers: { "Content-Type": "application/json", ...options.headers },
    ...options,
  });
  if (!res.ok) {
    const err = await res.text().catch(() => res.statusText);
    throw new Error(`${res.status}: ${err}`);
  }
  if (res.status === 204) return null;
  return res.json();
}

export const getClientes = () => fetchJson("/clientes");
export const getClienteById = (id) => fetchJson(`/clientes/${id}`);
export const createCliente = (data) =>
  fetchJson("/clientes", { method: "POST", body: JSON.stringify(data) });
export const loginCliente = (data) =>
  fetchJson("/clientes/login", { method: "POST", body: JSON.stringify(data) });

export const getEmpresas = () => fetchJson("/empresas");

export const getSolicitudes = () => fetchJson("/solicitudes");
export const getSolicitudesPublicas = () => fetchJson("/solicitudes/publicas");
export const getSolicitudById = (id) => fetchJson(`/solicitudes/${id}`);
export const createSolicitud = (data) =>
  fetchJson("/solicitudes", { method: "POST", body: JSON.stringify(data) });

export const getRespuestas = () => fetchJson("/respuestas");
export const createRespuesta = (data) =>
  fetchJson("/respuestas", { method: "POST", body: JSON.stringify(data) });

export const createComentarioSolicitud = (solicitudId, data) =>
  fetchJson(`/solicitudes/${solicitudId}/comentarios`, {
    method: "POST",
    body: JSON.stringify(data),
  });
