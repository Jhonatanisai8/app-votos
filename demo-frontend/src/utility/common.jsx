import Cookies from "js-cookie";
const TOKEN = "token";
export const guardarToken = (token) => {
  Cookies.set(TOKEN, token, { expires: 7 });
};
