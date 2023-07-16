import Axios from "./Axios";
import RequestErrorHandler from "../utils/RequestErrorHandler";
import ResponseErrorHandler from "../utils/ResponseErrorHandler";

export default class EventsRepository {
  static repository = Axios.axios;
  static url = Axios.url;

  static async getAllEvents() {
    try {
      return await this.repository.get(`${this.url}/events`);
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async getEventById(eventId){
    try {
        return await this.repository.get(`${this.url}/events/${eventId}`);
    } catch (error){
        return RequestErrorHandler(error);
    }
  }

  static async deleteEventById(eventId) {
    try {
      await this.repository.delete(`${this.url}/events/${eventId}`);
    } catch (error) {
      return ResponseErrorHandler(error);
    }
  }

  static async createEvent(eventData) {
    try {
      return await this.repository.post(`${this.url}/events`, eventData)
    } catch (error) {
      return ResponseErrorHandler(error);
    }
  }

  static async updateEvent(eventId, eventData) {
    try {
      await this.repository.put(`${this.url}/events/${eventId}`, eventData)
    } catch (error) {
      return ResponseErrorHandler(error);
    }
  }

  static async checkUserIsRegisteredForEvent(eventId, userId) {
    try {
      const response = await this.repository.get(`${this.url}/events/${eventId}/register/${userId}`);
      return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async approveEvent(eventId, isApproved) {
    try {
       await this.repository.put(`${this.url}/events/${eventId}`, {
         "isApproved": isApproved
       });
    } catch (error) {
        return ResponseErrorHandler(error);
    }
  }

  static async getEventRegistrations(eventId) {
    try {
       const response = await this.repository.get(`${this.url}/events/${eventId}/registrations`);
       return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async getEventGuests(eventId) {
    try {
        const response = await this.repository.get(`${this.url}/events/${eventId}/guests`);
        return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async getEventDietsCount(eventId) {
    try {
        const response = await this.repository.get(`${this.url}/events/${eventId}/diets`);
        return response;
    } catch (error) {
        return RequestErrorHandler(error);
    }
  }

  static async registerUser(eventId, userId, userNote) {
    const body = {
      "note": userNote
    }
    return await this.repository.post(`${this.url}/events/${eventId}/register/${userId}`, body);
  }

  static async unRegisterUser(eventId, userId) {
    try {
      await this.repository.delete(`${this.url}/events/${eventId}/unregister/${userId}`);
    } catch (error) {
      return ResponseErrorHandler(error);
    }
  }
  
  static async registerGuests(guests) {
    return await this.repository.post(`${this.url}/events/guests`, guests);
  }

  static async getOwnersEmailByEventId(eventId) {
    try {
        const response = await this.repository.get(`${this.url}/events/${eventId}/email`);
        return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async getEventsByRegistrationAndUser(userId) {
    try {
      const response = await this.repository.get(`${this.url}/events/registrations/${userId}`);
      return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async getUsersNotEnrolled(eventId) {
    try {
      const response = await this.repository.get(`${this.url}/events/${eventId}/reminder`);
      return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async getFeedbackByEventId(eventId) {
    try {
      const response = await this.repository.get(`${this.url}/events/${eventId}/feedback`);
      return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async getFeedbackByEventIdAndUserId(eventId, userId) {
    try {
      const response = await this.repository.get(`${this.url}/events/${eventId}/feedback/${userId}`);
      return response;
    } catch (error) {
      return RequestErrorHandler(error);
    }
  }

  static async createFeedback(eventId, userId, feedback) {
    try {
      const response = await this.repository.post(`${this.url}/events/${eventId}/feedback`, {
        "id": {
          "user_id": userId
        },
        "feedback": feedback
      });
      return response;
    } catch (error) {
      return ResponseErrorHandler(error);
    }
  }

  static async deleteFeedback(eventId, userId) {
    try {
      await this.repository.delete(`${this.url}/events/${eventId}/feedback/${userId}`);
    } catch (error) {
      return ResponseErrorHandler(error);
    }
  }
}
