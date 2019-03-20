import * as React from "react";

export default class LoginComponent extends React.Component<any, any> {

  constructor(props) {
    super(props);
    this.state = {
      username: null,
      password: null,
      loggedIn: null
    };
  }

  handleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value
    })
  };

  handleSubmit = () => {
    const request = new URLSearchParams();
    request.append('username', this.state.username);
    request.append('password', this.state.password);
    fetch('http://localhost:8081/api/login', {
      method: 'POST',
      body: request
    }).then(res => {
      if (!res.ok) {
        throw Error('Error: ' + res.status);
      }
      return;
    }).then(() => {
      this.setState({
        loggedIn: true
      })
    });
    event.preventDefault();
  };

  render() {
    const greetings = this.state.loggedIn ? <strong>Welcome {this.state.username}</strong> : '';

    return (
      <form onSubmit={this.handleSubmit}>
        <label>
          Username:
          <input name="username" placeholder="Username" type="text" onChange={this.handleChange} />
        </label>
        <label>
          Password:
          <input name="password" placeholder="Password" type="password" onChange={this.handleChange} />
        </label>
        <input type="submit" value="Login" />
        {greetings}
      </form>
    );
  }
}
