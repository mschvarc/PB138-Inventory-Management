import React, { Component } from 'react';

import SalesForm from './components/SalesForm';

class Sales extends Component {

	render() {
		return <div className="page-sales row">
      <div className="small-12 columns">
        <h2>Sales</h2>

				<SalesForm loadSales={this.props.loadSales} />

				<pre>
					{this.props.sales}
				</pre>
      </div>
    </div>
	}
}

export default Sales;
