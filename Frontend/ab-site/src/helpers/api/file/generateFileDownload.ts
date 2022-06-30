import axios from "axios";
import { resolve } from "path";
import config from "../../../config/config";
import { IApiResponse, IFile, STATUS_OK } from "../../typings";

export default (token: string, id: number): Promise<string> => {
  return new Promise((resolve) => {
    axios
      .get(`${config.API_URL}/file/id/${id}`, {
        headers: {
          Authentication: token,
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Headers":
            "Origin, X-Requested-With, Content-Type, Accept",
        },
      })
      .then((resp) => {
        let r: IApiResponse = resp.data;
        let file: IFile = r.result;

        if (r.status !== "OK") return "not ok";

        resolve(`data:${file.contentType};base64,${file.content}`);
      })
      .catch(() => {
        return "error";
      });
  });
};
