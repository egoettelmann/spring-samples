import * as React from 'react';
import { IRestError } from '@sample-spring-react/dtos';

interface ErrorProps {
  error: IRestError;
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