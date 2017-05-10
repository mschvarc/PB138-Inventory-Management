import React, { Component } from 'react';
import {Link} from 'react-router';

class Home extends Component {
	render() {

		var items = this.props.items;
		var categories = this.props.categories;


		return <div className="page-home row">
      <div className="small-12 columns">
        <h2>Welcome to Inventory Management!</h2>
      </div>

			<div className="small-12 large-6 columns">
				<p>Web application for keeping track of the current inventory for small shop. Features:</p>
				<ul>
					<li>Exploring of curent items and their categories.</li>
					<li>Provides daily/weekly/monthly overview of sales.</li>
					<li>Sends alerts when stock gets below a certain threshold defined per item</li>
					<li>Importing data about new shipments and past sales from XML files.</li>
					<li>Support importing shipments and sales via a SOAP API</li>
					<li>Developed by team of <a href="https://github.com/mschvarc/PB138-Inventory-Management/wiki/Assigned-Responsibilities">students</a> at Masaryk university as project for course <a href="https://is.muni.cz/predmet/fi/jaro2012/PB138?lang=en">PB138</a></li>
				</ul>
			</div>
      <div className="small-12 large-6 columns">
				<div className="row small-up-1 medium-up-2">
					<div className="columns">
		        <Link className="dashboard-nav-card" to="inventory">
		          <i className="dashboard-nav-card-icon fa fa-cubes" aria-hidden="true"></i>
		          <h3 className="dashboard-nav-card-title">{items.length} items</h3>
		        </Link>
		      </div>
		      <div className="columns">
		        <Link className="dashboard-nav-card" to="categories">
		          <i className="dashboard-nav-card-icon fa fa-folder-open-o" aria-hidden="true"></i>
		          <h3 className="dashboard-nav-card-title">{categories.length} categories</h3>
		        </Link>
		      </div>
		      <div className="columns">
		        <Link className="dashboard-nav-card" to="import-export">
		          <i className="dashboard-nav-card-icon fa fa-database" aria-hidden="true"></i>
		          <h3 className="dashboard-nav-card-title">Import & Export</h3>
		        </Link>
		      </div>
		      <div className="columns">
		        <Link className="dashboard-nav-card" to="sales">
		          <i className="dashboard-nav-card-icon fa fa-bar-chart" aria-hidden="true"></i>
		          <h3 className="dashboard-nav-card-title">Sales</h3>
		        </Link>
		      </div>
				</div>
			</div>
    </div>
	}
}

export default Home;
