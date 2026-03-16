import axiosInstance from "../../environment/axiosInstance";
export const signup = async (signupRequest) => {
  try {
    const response = await axiosInstance.post("api/auth/signup", signupRequest);
    return response;
  } catch (error) {
    throw error;
  }
};

export const login = async (loginRequest) => {
  try {
    const response = await axiosInstance.post("api/auth/login", loginRequest);
    return response;
  } catch (error) {
    throw error;
  }
};
