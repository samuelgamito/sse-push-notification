import axios, { AxiosResponse } from 'axios';
import { UserResponse } from '../schemas/User';

export default class UsersService {
  constructor() {
    axios.defaults.baseURL = 'http://localhost:8080/';
  }

  async getUsers(): Promise<UserResponse[]> {
    try {
      const response: AxiosResponse = await axios.get(
        'http://localhost:8080/users/'
      );

      const users: UserResponse[] = response.data;
      users.forEach((user) => {
        user.id = user.username;
      });
      return users;
    } catch (error) {
      console.log(error);
      return [];
    }
  }

  async upsertUser(user: UserResponse) {
    try {
      if (user.id) {
        await axios.patch(`http://localhost:8080/users/${user.id}`, user.alias);
      } else {
        await axios.post('http://localhost:8080/users/', user);
      }
    } catch (error) {
      console.log(error);
    }
  }
}
