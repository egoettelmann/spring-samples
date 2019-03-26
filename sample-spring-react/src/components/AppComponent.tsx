import * as React from 'react';
import MainComponent from './MainComponent';
import LoginComponent from './LoginComponent';
import ErrorComponent from './ErrorComponent';
import { IAppUserDetails, IRestError } from '@sample-spring-react/dtos';
import api from '../services/api';

interface AppState {
    loaded: boolean;
    user?: IAppUserDetails;
    error?: IRestError;
}

export default class AppComponent extends React.Component<any, AppState> {

    private timeoutHandle: number;

    constructor(props) {
        super(props);
        this.state = {
            loaded: false
        };
        this.registerErrorHandler();
        this.loadSession();
    }

    onLoginChange = () => {
        this.loadSession();
    };

    render() {
        let content;
        if (this.state.loaded && this.state.user) {
            content = <MainComponent user={this.state.user} onChange={this.onLoginChange}/>;
        } else if (this.state.loaded) {
            content = <LoginComponent onChange={this.onLoginChange}/>;
        } else {
            content = <br/>;
        }
        return (
            <div>
                <h1>Sample Spring React App</h1>
                { content }
                <ErrorComponent error={this.state.error}/>
            </div>
        );
    };

    private loadSession = () => {
        api.get('/session').then((response) => {
            this.setState({
                loaded: true,
                user: response.data
            });
        }, (error) => {
            console.error(error);
            this.setState({
                loaded: true,
                user: undefined
            });
        });
    };

    private registerErrorHandler = () => {
        api.interceptors.response.use((response) => {
            return response;
        }, (error) => {
            this.setState({
                error: error.response.data
            });
            if (this.timeoutHandle) {
                clearTimeout(this.timeoutHandle);
                this.timeoutHandle = undefined;
            }
            this.timeoutHandle = setTimeout(() => {
                this.setState({
                    error: undefined
                });
            }, 2000);
            return Promise.reject(error.response.data);
        });
    };

}