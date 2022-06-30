import axios from "axios";
import config from "../../../config/config";
import { IApiResponse } from "../../typings";

export default (token: string, formData: FormData): Promise<IApiResponse> => {
  return axios
    .post(`${config.API_URL}/file`, formData, {
      headers: {
        Authentication: token,
        "Content-Type": "multipart/form-data",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers":
          "Origin, X-Requested-With, Content-Type, Accept",
      },
    })
    .then((res) => Promise.resolve(res.data))
    .catch((err) => Promise.reject(err));
};
