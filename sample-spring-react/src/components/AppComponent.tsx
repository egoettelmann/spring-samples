import * as React from 'react';
import SampleComponent from './SampleComponent';
import LoginComponent from './LoginComponent';
import { User } from '../models/User';
import axios from 'axios';
import ErrorComponent from './ErrorComponent';
import { RestError } from '../models/RestError';

interface AppState {
  user?: User;
  error?: RestError;
}

export default class AppComponent extends React.Component<any, AppState> {

  constructor(props) {
    super(props);
    this.state = {};
    axios.interceptors.response.use((response) => {
      return response;
    }, (error) => {
      this.setState({
        error: error.response.data
      });
      return Promise.reject(error.response.data);
    });
  }

  onLoginChange = (user?: User) => {
    this.setState({
      user: user
    });
  };

  render() {
    return (
      <div>
        <h1>Sample Spring React App</h1>
        <LoginComponent onChange={this.onLoginChange}/>
        <ErrorComponent error={this.state.error}/>
        <SampleComponent user={this.state.user}/>
      </div>
    )
  };

}