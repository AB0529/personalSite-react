import axios from "axios";
import config from "../../../config/config";
import { IApiResponse } from "../../typings";

export default (username: string, password: string): Promise<IApiResponse> => {
  return axios
    .post(
      `${config.API_URL}/auth/login`,
      { username, password },
      {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Headers":
            "Origin, X-Requested-With, Content-Type, Accept",
        },
      }
    )
    .then((res) => Promise.resolve(res.data))
    .catch((err) => Promise.reject(err));
};
