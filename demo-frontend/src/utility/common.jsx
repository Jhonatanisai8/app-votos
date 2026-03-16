import Cookies from "js-cookie";
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
