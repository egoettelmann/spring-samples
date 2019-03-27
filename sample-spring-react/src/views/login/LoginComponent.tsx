import * as React from 'react';
import { Button, Form, Segment } from 'semantic-ui-react';
import api from '../../services/api';

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
            <Form onSubmit={this.tryLogin} style={{ maxWidth: '450px', margin: 'auto', marginTop: '50px' }}>
                <Form size='large'>
                    <Segment>
                        <Form.Input fluid icon='user'
                                    iconPosition='left'
                                    placeholder='Username'
                                    type='text'
                                    name='username'
                                    onChange={this.formChange}
                        />
                        <Form.Input fluid icon='lock'
                                    iconPosition='left'
                                    placeholder='Password'
                                    type='password'
                                    name='password'
                                    onChange={this.formChange}
                        />
                        <Button fluid size='large' type='submit'>Login</Button>
                    </Segment>
                </Form>
            </Form>
        );
    }
}
