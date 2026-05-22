import { createContext, useContext, useState, useEffect } from "react";
import { loginCliente, createCliente } from "../api/client.js";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const stored = localStorage.getItem("requestructure_user");
    if (stored) {
      try {
        setUser(JSON.parse(stored));
      } catch {
        localStorage.removeItem("requestructure_user");
      }
    }
    setLoading(false);
  }, []);

  async function login(email, password) {
    const found = await loginCliente({ email, password });
    if (!found) return false;
    const userData = {
      id: found.id,
      username: found.username,
      email: found.email,
      direccion: found.direccion,
    };
    setUser(userData);
    localStorage.setItem("requestructure_user", JSON.stringify(userData));
    return true;
  }

  function logout() {
    setUser(null);
    localStorage.removeItem("requestructure_user");
  }

  async function register(data) {
    await createCliente(data);
    return true;
  }

  const isGuest = !user;

  return (
    <AuthContext.Provider value={{ user, isGuest, loading, login, logout, register }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error("useAuth must be used within AuthProvider");
  return ctx;
}
