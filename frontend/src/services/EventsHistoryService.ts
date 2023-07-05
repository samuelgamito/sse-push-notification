import axios, { AxiosResponse } from 'axios';
import { EventHistory } from '../schemas/Events';

export default class EventsHistoryService {
  async getEventHistory(): Promise<EventHistory[]> {
    try {
      const response: AxiosResponse = await axios.get(
        'http://localhost:8080/event/history'
      );

      const events: EventHistory[] = response.data;
      return events;
    } catch (error) {
      console.log(error);
      return [];
    }
  }
}
