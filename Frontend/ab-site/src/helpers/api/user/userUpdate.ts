import axios from "axios";
import config from "../../../config/config";

export default (
  token: string,
  username: string,
  oldUsername: string,
  password: string,
  email: string,
  oldEmail: string,
  firstName: string,
  lastName: string
) => {
  return axios
    .post(
      `${config.API_URL}/user/update`,
      {
        username,
        oldUsername,
        password,
        email,
        oldEmail,
        firstName,
        lastName,
      },
      {
        headers: {
          Authentication: token,
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Headers":
            "Origin, X-Requested-With, Content-Type, Accept",
        },
      }
    )
    .then((res) => Promise.resolve(res.data))
    .catch((err) => Promise.reject(err));
};
