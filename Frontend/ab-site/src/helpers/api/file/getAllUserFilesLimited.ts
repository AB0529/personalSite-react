import axios from "axios";
import config from "../../../config/config";
import { IApiResponse } from "../../typings";

export default (token: string, max: number): Promise<IApiResponse> => {
  return axios
    .get(`${config.API_URL}/file/all/user/${max}`, {
      headers: {
        Authentication: token,
      },
    })
    .then((res) => Promise.resolve(res.data))
    .catch((err) => Promise.reject(err));
};
