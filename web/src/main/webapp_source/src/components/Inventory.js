import React, { Component } from 'react';
import InventoryTable from './components/InventoryTable';

class Inventory extends Component {
	render() {

		var items = this.props.route.items;
		var categories = this.props.route.categories;

		return <div className="page-inventory row">
      <div className="small-12 columns">
        <h2>Current Inventory</h2>

				<InventoryTable items={items} categories={categories} />

      </div>
    </div>
	}
}

export default Inventory;
