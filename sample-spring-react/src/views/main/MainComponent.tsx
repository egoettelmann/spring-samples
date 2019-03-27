import * as React from 'react';
import { Container, Header, Menu } from 'semantic-ui-react';
import { IAppUserDetails } from '../../../ts-gen/dtos';
import api from '../../services/api';
import ToolbarComponent from '../../components/ToolbarComponent';
import { BrowserRouter, Link, Route } from 'react-router-dom';
import Tab1Component from './Tab1Component';
import Tab2Component from './Tab2Component';

interface MainProps {
    user?: IAppUserDetails;
    onLogout?: () => void;
    match?: any;
}

export default class MainComponent extends React.Component<MainProps, any> {

    constructor(props) {
        super(props);
        this.state = {};
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
                <Container>
                    <Header as='h1' style={{ marginTop: '1rem' }}>A Simple React Component with Typescript</Header>
                    <p>I am a component which shows a random image from RoboHash. For more info on RoboHash, please visit <a
                        href="https://robohash.org">https://robohash.org</a></p>
                    <BrowserRouter>
                        <div>
                            <Menu pointing secondary>
                                <Menu.Item name='tab1'>
                                    <Link to="/tab1">Tab 1</Link>
                                </Menu.Item>
                                <Menu.Item name='tab2'>
                                    <Link to="/tab2">Tab 2</Link>
                                </Menu.Item>
                            </Menu>
                            <Route exact path="/tab1" component={Tab1Component}/>
                            <Route exact path="/tab2" component={Tab2Component}/>
                        </div>
                    </BrowserRouter>
                </Container>
            </div>
        );
    }
}
