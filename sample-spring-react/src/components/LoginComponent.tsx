import * as React from 'react';
import api from '../services/api';

interface LoginProps {
    onLogin?: () => void;
}

interface LoginState {
    username?: string;
    password?: string;
}

export default class LoginComponent extends React.Component<LoginProps, LoginState> {

    constructor(props) {
        super(props);
        this.state = {
            username: null,
            password: null
        };
    }

    formChange = (event) => {
        const newState = {};
        newState[event.target.name] = event.target.value;
        this.setState(newState);
    };

    tryLogin = () => {
        const request = new URLSearchParams();
        request.append('username', this.state.username);
        request.append('password', this.state.password);
        api.post('/login', request)
            .then(() => {
                if (this.props.onLogin) {
                    this.props.onLogin();
                }
            })
            .catch((err) => {
                console.log('Error:', err);
            });
        event.preventDefault();
    };


    render() {
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
    }
}
