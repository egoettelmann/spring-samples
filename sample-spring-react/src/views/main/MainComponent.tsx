import * as React from 'react';
import { IAppUserDetails } from '../../../ts-gen/dtos';
import api from '../../services/api';
import ToolbarComponent from '../../components/ToolbarComponent';
import { BrowserRouter, Link, Route } from 'react-router-dom';
import Tab1Component from './Tab1Component';
import Tab2Component from './Tab2Component';

interface SampleProps {
    user?: IAppUserDetails;
    onLogout?: () => void;
}

export default class MainComponent extends React.Component<SampleProps, any> {

    constructor(props) {
        super(props);
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

    render() {
        return (
            <div>
                <ToolbarComponent user={this.props.user} onLogout={this.logout}/>
                <h2>A Simple React Component with Typescript</h2>
                <p>I am a component which shows a random image from RoboHash. For more info on RoboHash, please visit <a
                    href="https://robohash.org">https://robohash.org</a></p>
                <BrowserRouter>
                    <div>
                        <ul>
                            <li>
                                <Link to="/tab1">Tab 1</Link>
                            </li>
                            <li>
                                <Link to="/tab2">Tab 2</Link>
                            </li>
                        </ul>
                        <hr/>
                        <Route exact path="/tab1" component={Tab1Component}/>
                        <Route exact path="/tab2" component={Tab2Component}/>
                    </div>
                </BrowserRouter>
            </div>
        );
    }
}
