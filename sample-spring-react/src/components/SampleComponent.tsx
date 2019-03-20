import * as React from 'react';
import { User } from '../models/User';

interface SampleProps {
  user?: User;
}

export default class SampleComponent extends React.Component<SampleProps, any> {

  private readonly imgPath;

  constructor(props) {
    super(props);
    this.imgPath = this.generateLogoPath();
  }

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
      </div>
    );
  }
}
