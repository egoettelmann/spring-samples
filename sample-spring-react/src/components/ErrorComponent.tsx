import * as React from 'react';
import { IRestError } from '@sample-spring-react/dtos';

interface ErrorProps {
    error: IRestError;
}

export default class ErrorComponent extends React.Component<ErrorProps, any> {

    style = {
        margin: '1rem',
        padding: '.5rem',
        color: '#721c24',
        backgroundColor: '#f8d7da',
        border: '1px solid #f5c6cb',
        borderRadius: '.5rem'
    };

    constructor(props) {
        super(props);
    }

    render() {
        if (this.props.error) {
            return (
                <div style={ this.style }>
                    {this.props.error.code}: {this.props.error.translationKey}
                </div>
            );
        }
        return <br/>;
    }

}