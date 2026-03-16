import Cookies from "js-cookie";
import { jwtDecode } from "jwt-decode";
const TOKEN = "token";

export const guardarToken = (token) => {
  Cookies.set(TOKEN, token, { expires: 7 });
};

export const obtenerToken = () => {
  return Cookies.get(TOKEN) || null;
};

export const eliminarToken = () => {
  Cookies.remove(TOKEN);
};

export const decodificarToken = () => {
  const token = obtenerToken();
  if (!token) {
    return null;
  }
  try {
    return jwtDecode(token);
  } catch (error) {
    console.log("Error al decodificar el token", error);
    return null;
  }
};

export const esTokenValido = () => {
  const tokenDecodificado = decodificarToken();
  if (!tokenDecodificado || !tokenDecodificado.exp) {
    return false;
  }
  const experirado = tokenDecodificado.exp * 1000;
  return Date.now() < experirado;
};
