import { AxiosError } from "axios";
import { IApiResponse } from "../typings";

export default (err: AxiosError) => {
	// Network error
	if (err.code === "ERR_NETWORK")
		return { error: true, msg: "Error: Cannot estasblish link to server!" };
	// API error
	else if (err.response)
		return { error: true, msg: (err.response.data as IApiResponse).message };

	// Whatever else error
	console.error(err);
	return { error: true, msg: err.message };
}
