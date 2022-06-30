import axios from "axios";
import config from "../../../config/config";
import { IApiResponse } from "../../typings";

export default (token: string, id: number): Promise<IApiResponse> => {
  return axios
    .delete(`${config.API_URL}/user/delete/${id}`, {
      headers: {
        Authentication: token,
      },
    })
    .then((res) => Promise.resolve(res.data))
    .catch((err) => Promise.reject(err));
};
