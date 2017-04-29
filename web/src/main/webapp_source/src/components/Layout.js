import React, { Component } from 'react';
import {Link} from 'react-router';

class Layout extends Component {
	render() {
		return <div className="app-dashboard shrink-medium">
      <div className="row expanded app-dashboard-top-nav-bar">
        <div className="columns medium-2">
          <button data-toggle="app-dashboard-sidebar" className="menu-icon hide-for-medium"></button>
          <Link to="/" className="app-dashboard-logo">Inventory Management</Link>
        </div>
      </div>

      <div className="app-dashboard-body off-canvas-wrapper">
        <div id="app-dashboard-sidebar" className="app-dashboard-sidebar position-left off-canvas off-canvas-absolute reveal-for-medium" data-off-canvas>
          <div className="app-dashboard-sidebar-title-area">
            <div className="app-dashboard-close-sidebar">
              <button id="close-sidebar" data-app-dashboard-toggle-shrink className="app-dashboard-sidebar-close-button show-for-medium" aria-label="Close menu" type="button">
                <span aria-hidden="true"><a href="#"><i className="large fa fa-angle-double-left"></i></a></span>
              </button>
            </div>
            <div className="app-dashboard-open-sidebar">
              <button id="open-sidebar" data-app-dashboard-toggle-shrink className="app-dashboard-open-sidebar-button show-for-medium" aria-label="Open menu" type="button">
                <span aria-hidden="true"><a href="#"><i className="large fa fa-angle-double-right"></i></a></span>
              </button>
            </div>
          </div>
          <div className="app-dashboard-sidebar-inner">
            <ul className="menu vertical">
						<li>
							<Link to="/">
								<i className="large fa fa-dashboard"></i><span className="app-dashboard-sidebar-text">Dashboard</span>
							</Link>
						</li>
							<li>
								<Link to="inventory">
                	<i className="large fa fa-cubes"></i><span className="app-dashboard-sidebar-text">Inventory</span>
              	</Link>
							</li>
              <li>
								<Link to="categories">
                	<i className="large fa fa-folder-open-o"></i><span className="app-dashboard-sidebar-text">Categories</span>
              	</Link>
							</li>
              <li>
								<Link to="import-export">
                	<i className="large fa fa-database"></i><span className="app-dashboard-sidebar-text">Import & Export</span>
              	</Link>
							</li>
              <li>
								<Link to="sales">
                	<i className="large fa fa-bar-chart"></i><span className="app-dashboard-sidebar-text">Sales</span>
              	</Link>
							</li>
            </ul>
          </div>
        </div>

        <div className="app-dashboard-body-content off-canvas-content" data-off-canvas-content>
          {this.props.children}
        </div>
      </div>
    </div>
	}
}

export default Layout;
