import * as React from 'react';
import axios from 'axios';
import { User } from '../models/User';

interface LoginProps {
  onChange?: (user?: User) => void
}

interface LoginState extends User {
  loggedIn: boolean;
}

export default class LoginComponent extends React.Component<LoginProps, LoginState> {

  constructor(props) {
    super(props);
    this.state = {
      username: null,
      password: null,
      loggedIn: null
    };
  }

  formChange = (event) => {
    const newState = {};
    newState[event.target.name] = event.target.value;
    this.setState(newState);
  };

  logout = () => {
    axios.post('http://localhost:8081/api/logout')
      .then(() => {
        this.setState({
          loggedIn: false
        });
        this.notify(false);
      }).catch((err) => {
        console.log('Error:', err);
      });
  };

  tryLogin = () => {
    const request = new URLSearchParams();
    request.append('username', this.state.username);
    request.append('password', this.state.password);
    axios.post('http://localhost:8081/api/login', request)
      .then(() => {
        this.setState({
          loggedIn: true
        });
        this.notify(true);
      }).catch((err) => {
        console.log('Error:', err);
      });
    event.preventDefault();
  };

  notify = (loggedIn: boolean) => {
    if (this.props.onChange) {
      let user;
      if (loggedIn) {
        user = {
          username: this.state.username,
          password: this.state.password
        };
      }
      this.props.onChange(user);
    }
  };

  render() {
    if (!this.state.loggedIn) {
      return (
        <form onSubmit={this.tryLogin}>
          <label>
            Username:
            <input name="username" placeholder="Username" type="text" onChange={this.formChange}/>
          </label>
          <br/>
          <label>
            Password:
            <input name="password" placeholder="Password" type="password" onChange={this.formChange}/>
          </label>
          <input type="submit" value="Login"/>
        </form>
      );
    } else {
      return <button onClick={this.logout}>Logout</button>
    }
  }
}
