import Axios from "./Axios";
import RequestErrorHandler from "../utils/RequestErrorHandler";

export default class UsersRepository {
  static repository = Axios.axios;
  static url = Axios.url;

  /**
   * Validate whether the user credentials are valid
   * @param {*} email The email address of the user
   * @param {*} password The password of the user
   * @returns The user object containing the user_id, email and role if the credentials are valid, otherwise an error
   */
  static async validateUserCredentials(email, password) {
    return await this.repository.post(`${this.url}/users/authenticate`, {
      email: email,
      password: password
    });
  }

  /**
   * Get a user by its id
   * @param {*} id The id of the user
   * @returns The user object containing the user_id, email and role if the user exists, otherwise a 404 error
   */
  static async getUserById(id) {
    try {
      return await this.repository.get(`${this.url}/users/${id}`);
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }
}
