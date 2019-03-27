import * as React from 'react';
import { Dropdown, Icon, Image, Menu } from 'semantic-ui-react';
import { IAppUserDetails } from '@sample-spring-react/dtos';

interface ToolbarProps {
    user: IAppUserDetails;
    onLogout: () => void;
}

export default class ToolbarComponent extends React.Component<ToolbarProps, any> {

    imgPath: string;

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

    avatar = () => {
        return (
            <span>
                <Image avatar src={this.imgPath}/>
            </span>
        );
    };

    render() {
        return (
            <Menu secondary attached='top'>
                <Menu.Menu position='left'>
                    <Menu.Item name='Sample Spring-React App'/>
                </Menu.Menu>
                <Menu.Menu position='right'>
                    <Dropdown trigger={ this.avatar() } item simple icon={null}>
                        <Dropdown.Menu>
                            <Dropdown.Item>{this.props.user.username}</Dropdown.Item>
                            <Dropdown.Item onClick={this.logout}>
                                <Icon name='sign-out' />
                                <span className='text'>Logout</span>
                            </Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                </Menu.Menu>
            </Menu>
        );
    }

}