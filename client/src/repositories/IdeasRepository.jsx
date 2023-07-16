import Axios from "./Axios";

export default class IdeasRepository {
    static repository = Axios.axios;
    static url = Axios.url;

    static async getAllIdeas() {
      try {
        const response = await this.repository.get(`${this.url}/ideas`);
        return response.data;
      } catch (error) {
        return error;
      }
    }
    static async createIdea(idea, userId) {
      try {
        const body = {
          "user_id": userId,
          "idea": idea,
          "likes": 0
        }
        const response = await this.repository.post(`${this.url}/ideas`, body);
        return response;
      } catch (error) {
        return error;
      }
    }
    static async deleteIdeaById(id) {
      try {
        const response = await this.repository.delete(`${this.url}/ideas/${id}`);
        return response;
      } catch (error) {
        return error;
      }
    }
    static async likeIdea(ideaId, userId) {
      try {
        const response = await this.repository.put(`${this.url}/ideas/${ideaId}/likes/${userId}?liked=true`);
        return response;
      } catch (error) {
        return error;
      }
    }
    static async dislikeIdea(ideaId, userId) {
      try {
        const response = await this.repository.put(`${this.url}/ideas/${ideaId}/likes/${userId}?liked=false`);
        return response;
      } catch (error) {
        return error;
      }
    }
    static async checkUserHasLiked(ideaId, userId) {
      try {
        const response = await this.repository.get(`${this.url}/ideas/${ideaId}/checklike/${userId}`);
        return response.data;
      } catch (error) {
        return error;
      }
    }
}
