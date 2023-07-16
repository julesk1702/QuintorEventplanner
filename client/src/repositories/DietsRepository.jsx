import Axios from "./Axios";
import RequestErrorHandler from "../utils/RequestErrorHandler";

export default class DietsRepository {
  static repository = Axios.axios;
  static url = Axios.url;

  /**
   * Get all diets from the database
   * @returns All diets with their id and name
   */
  static async getDiets() {
    try {
      return await this.repository.get(`${this.url}/diets`);
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  /**
   * Set the diets of a user
   * @param {*} userId The id of the user
   * @param {Array} diets An array containing the ids of the diets
   * @returns The response of the request
   */
  static async setDiets(userId, diets, customDiets) {
    const body = {
      "dietIds": diets,
      "customDiets": customDiets
    };
    return await this.repository.post(`${this.url}/users/${userId}/diets`, body);
  }

  /**
   * Gets the diets of a user
   * @param {*} userId The id of the user
   * @returns The diets of the user
   */
  static async getDietsByUserId(userId) {
    try {
      return await this.repository.get(`${this.url}/users/${userId}/diets`);
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  /**
   * Gets the custom diets of a user
   * @param {*} userId The id of the user
   * @returns The custom diets of the user
    */
  static async getCustomDietsByUserId(userId) {
    try {
      return await this.repository.get(`${this.url}/users/${userId}/custom-diets`);
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }
}
