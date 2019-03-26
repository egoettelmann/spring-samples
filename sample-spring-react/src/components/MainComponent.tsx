import * as React from 'react';
import { IAppUserDetails } from '@sample-spring-react/dtos';
import api from '../services/api';

interface SampleProps {
    user?: IAppUserDetails;
    onLogout?: () => void;
}

export default class MainComponent extends React.Component<SampleProps, any> {

    private readonly imgPath;

    constructor(props) {
        super(props);
        this.imgPath = this.generateLogoPath();
    }

    logout = () => {
        api.post('/logout')
            .then(() => {
                if (this.props.onLogout) {
                    this.props.onLogout();
                }
            })
            .catch((err) => {
                console.log('Error:', err);
            });
    };

    generateLogoPath = () => {
        const hash = Math.random().toString(36).slice(2);
        return `https://robohash.org/${hash}.png`;
    };

    render() {
        return (
            <div>
                <h2>A Simple React Component with Typescript {this.props.user ? this.props.user.username : ''}</h2>
                <div>
                    <img src={this.imgPath} alt="Random image"/>
                </div>
                <p>I am a component which shows a random image from RoboHash. For more info on RoboHash, please visit <a
                    href="https://robohash.org">https://robohash.org</a></p>
                <button onClick={this.logout}>Logout</button>
            </div>
        );
    }
}
