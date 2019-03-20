import * as React from 'react';
import * as ReactDOM from 'react-dom';
import SampleComponent from './components/SampleComponent';
import LoginComponent from './components/LoginComponent';

ReactDOM.render(
  <div>
    <h1>Sample Spring React App</h1>
    <SampleComponent/>
    <LoginComponent/>
  </div>,
  document.getElementById('root')
);
