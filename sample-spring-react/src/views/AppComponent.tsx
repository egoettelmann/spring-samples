import * as React from 'react';
import MainComponent from './main/MainComponent';
import LoginComponent from './login/LoginComponent';
import ErrorComponent from '../components/ErrorComponent';
import { IAppUserDetails, IRestError } from '../../ts-gen/dtos';
import api from '../services/api';

interface AppState {
    loaded: boolean;
    user?: IAppUserDetails;
    error?: IRestError;
}

export default class AppComponent extends React.Component<any, AppState> {

    private errorTimeoutHandle: number;

    constructor(props) {
        super(props);
        this.state = {
            loaded: false
        };
        this.registerErrorHandler();
        this.loadSession();
    }

    render() {
        // If session not loaded yet, we wait
        let content;
        if (!this.state.loaded) {
            content = <br/>; // FIXME: should be a spinner
        } else if (this.state.user) {
            content = <MainComponent user={this.state.user} onLogout={this.logout} />;
        } else {
            content = <LoginComponent onLogin={this.loadSession} />
        }
        return (
            <div>
                {content}
                <ErrorComponent error={this.state.error}/>
            </div>
        );
    };

    private logout = () => {
        this.setState({
            user: undefined
        });
    };

    private loadSession = () => {
        api.get('/session').then((response) => {
            this.setState({
                loaded: true,
                user: response.data
            });
        }, () => {
            this.setState({
                loaded: true,
                user: undefined
            });
        });
    };

    private registerErrorHandler = () => {
        api.interceptors.response.use((response) => {
            return response;
        }, (error: IRestError) => {
            if (error.code === 'A010') {
                this.setState({
                    user: undefined
                });
            }
            this.displayError(error);
            return Promise.reject(error);
        });
    };

    private displayError = (error: IRestError) => {
        this.setState({
            error: error
        });
        if (this.errorTimeoutHandle) {
            clearTimeout(this.errorTimeoutHandle);
        }
        setTimeout(() => {
            this.setState({
                error: undefined
            });
            this.errorTimeoutHandle = undefined;
        }, 5000)
    };

}
