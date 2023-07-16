import axios from "axios";

const URL = process.env.REACT_APP_API_URL;
axios.defaults.headers.patch["Access-Control-Allow-Methods"] = "*";
axios.defaults.headers["Access-Control-Allow-Origin"] = "*";

// eslint-disable-next-line import/no-anonymous-default-export
export default {
  axios,
  url: URL,
}
