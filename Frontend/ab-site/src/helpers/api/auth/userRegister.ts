import axios from "axios";
import config from "../../../config/config";

export default (username: string, password: string, email: string, firstName: string, lastName: string) => {
	return axios.post(`${config.API_URL}/auth/register`, {username, password, email, firstName, lastName})
	.then(res => Promise.resolve(res.data))
	.catch(err => Promise.reject(err));
}