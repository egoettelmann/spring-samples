import * as React from 'react';
import { IAppUserDetails } from '@sample-spring-react/dtos';

interface ToolbarProps {
    user: IAppUserDetails;
    onLogout: () => void;
}

export default class ToolbarComponent extends React.Component<ToolbarProps, any> {

    imgPath: string;

    style = {
        margin: '0',
        padding: '.5rem',
        backgroundColor: '#c3c3c3',
        display: 'flex',
        justifyContent: 'space-between'
    };

    constructor(props) {
        super(props);
        this.imgPath = this.generateAvatarPath();
    }

    logout = () => {
        this.props.onLogout();
    };

    generateAvatarPath = () => {
        const hash = Math.random().toString(36).slice(2);
        return `https://robohash.org/${hash}.png?size=30x30`;
    };

    render() {
        return (
            <div style={ this.style }>
                <div>Sample Spring-React App</div>
                <div style={{ display: 'flex', alignItems: 'stretch' }}>
                    <img style={{ backgroundColor: 'white', borderRadius: '50%' }} src={this.imgPath} alt="Avatar"/>
                    <div>{ this.props.user.username }</div>
                    <button onClick={this.logout}>Logout</button>
                </div>
            </div>
        );
    }

}