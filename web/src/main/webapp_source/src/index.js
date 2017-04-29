import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';

import './style/app.scss';

import './javascript/app.js';
import './javascript/dashboard-layout.js';

import 'foundation-sites/dist/js/foundation.js';

ReactDOM.render(
  <App />,
  document.getElementById('root')
);
