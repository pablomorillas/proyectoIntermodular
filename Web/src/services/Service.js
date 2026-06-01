import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const getSolicitudes = async () => {
  try {
    const res = await axios.get(`${API_URL}/solicitudes`);
    return res.data ?? [];
  } catch (error) {
    console.error("Error al obtener solicitudes:", error);
    throw new Error(error.response?.data?.message || "No se pudieron cargar las solicitudes.");
  }
};

export const getSolicitudesPublicas = async () => {
  try {
    const res = await axios.get(`${API_URL}/solicitudes/publicas`);
    return res.data ?? [];
  } catch (error) {
    console.error("Error al obtener solicitudes publicas:", error);
    throw new Error(error.response?.data?.message || "No se pudieron cargar las solicitudes publicas.");
  }
};

export const getSolicitudById = async (id) => {
  try {
    const res = await axios.get(`${API_URL}/solicitudes/${id}`);
    return res.data;
  } catch (error) {
    console.error(`Error al obtener la solicitud ${id}:`, error);
    throw new Error(error.response?.data?.message || "No se pudo obtener la solicitud.");
  }
};

export const createSolicitud = async (data) => {
  try {
    const res = await axios.post(`${API_URL}/solicitudes`, data);
    return res.data;
  } catch (error) {
    console.error("Error al crear la solicitud:", error);
    throw new Error(error.response?.data?.message || "No se pudo crear la solicitud.");
  }
};

export const getRespuestas = async () => {
  try {
    const res = await axios.get(`${API_URL}/respuestas`);
    return res.data ?? [];
  } catch (error) {
    console.error("Error al obtener respuestas:", error);
    throw new Error(error.response?.data?.message || "No se pudieron cargar las respuestas.");
  }
};

export const loginCliente = async (data) => {
  try {
    const res = await axios.post(`${API_URL}/clientes/login`, data);
    return res.data;
  } catch (error) {
    console.error("Error al iniciar sesion:", error);
    throw new Error(error.response?.data?.message || "Credenciales incorrectas.");
  }
};

export const createCliente = async (data) => {
  try {
    const res = await axios.post(`${API_URL}/clientes`, data);
    return res.data;
  } catch (error) {
    console.error("Error al registrar cliente:", error);
    throw new Error(error.response?.data?.message || "No se pudo registrar el usuario.");
  }
};
