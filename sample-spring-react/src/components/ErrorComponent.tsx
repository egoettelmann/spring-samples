import * as React from 'react';
import { RestError } from '../models/RestError';

interface ErrorProps {
  error: RestError;
}

export default class ErrorComponent extends React.Component<ErrorProps, any> {

  constructor(props) {
    super(props);
  }

  render() {
    if (this.props.error) {
      return (
        <div style={{ color: 'red' }}>
          {this.props.error.code}: {this.props.error.translationKey}
        </div>
      );
    }
    return <br/>;
  }

}