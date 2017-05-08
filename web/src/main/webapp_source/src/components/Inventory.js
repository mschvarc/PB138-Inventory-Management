import React, { Component } from 'react';
import InventoryTable from './components/InventoryTable';

class Inventory extends Component {
	render() {

		var items = this.props.items;

		return <div className="page-inventory row">
      <div className="small-12 columns">
        <h2>Current Inventory</h2>

				<InventoryTable items={items} />

      </div>
    </div>
	}
}

export default Inventory;
