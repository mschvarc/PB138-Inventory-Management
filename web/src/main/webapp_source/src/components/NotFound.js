import React, { Component } from 'react';
import {Link} from 'react-router';

class NotFound extends Component {
	render() {
		return <div className="page-not-found row">
			<div className="small-12 columns">
	      <h2>Page not found!</h2>
	      <p>Page you requested cannot be found in this application! What will you do about it?</p>
	      <ul>
	        <li><Link to="/">Return to dashboard!</Link></li>
	        <li><a href="https://github.com/mschvarc/PB138-Inventory-Management">Submit pull request to add this site!</a></li>
	      </ul>
			</div>
    </div>
	}
}

export default NotFound;
