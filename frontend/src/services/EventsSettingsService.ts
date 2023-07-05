import axios, { AxiosResponse } from 'axios';
import { EventsSettingsResponse } from '../schemas/Events';

export default class EventsSettingsService {
  constructor() {
    axios.defaults.baseURL = 'http://localhost:8080/';
  }

  async getEventsSettings(): Promise<EventsSettingsResponse[]> {
    try {
      const response: AxiosResponse = await axios.get(
        'http://localhost:8080/settings/'
      );

      const users: EventsSettingsResponse[] = response.data;
      users.forEach((user) => {
        user.id = user.alias;
      });
      return users;
    } catch (error) {
      console.log(error);
      return [];
    }
  }

  async createEventsSettings(eventSettings: EventsSettingsResponse) {
    try {
      await axios.post('http://localhost:8080/settings/', eventSettings);
    } catch (error) {
      console.log(error);
      return [];
    }
  }
}
