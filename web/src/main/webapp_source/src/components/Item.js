import React, { Component } from 'react';
import {Link} from 'react-router';

class Item extends Component {
	render() {

		var items = this.props.items;
		var item = items.find(item => {return item.ean === this.props.paramItem});

		if(item) {
			return <div className="page-item row">
	      <div className="small-12 columns">
	        <h2>Item {item.name} <small>Id: {item.id} Ean: {item.ean}</small></h2>
					<dl>
						<dt>Description</dt>
						<dd>{item.description}</dd>
						<hr/>
						<dt>Current amount</dt>
						<dd>
							<div className="row">
								<div className="small-2 columns"><input type="number" min="0" value={item.currentCount} /></div>
								<div className="small-10 columns"><input value={item.unit} /></div>
							</div>
						</dd>
	        	<dt>Category</dt>
						<dd><Link to={"category/"+item.category.id}>{item.category.name}</Link></dd>
	        	<dt>Alert threshold</dt>
						<dd><input type="number" min="-1" value={item.alertThreshold} /></dd>
					</dl>
	      </div>
	    </div>
		} else {
			return <div className="page-item row">
	      <div className="small-12 columns">
	        <h2>Item not found</h2>
	      </div>
	    </div>
		}
	}
}

export default Item;
