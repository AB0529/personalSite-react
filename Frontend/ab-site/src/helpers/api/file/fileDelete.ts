import axios from "axios";
import config from "../../../config/config";
import { IApiResponse } from "../../typings";

export default (token: string, id: string): Promise<IApiResponse> => {
  return axios
    .delete(`${config.API_URL}/file/delete/${id}`, {
      headers: {
        Authentication: token,
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers":
          "Origin, X-Requested-With, Content-Type, Accept",
      },
    })
    .then((res) => Promise.resolve(res.data))
    .catch((err) => Promise.reject(err));
};
