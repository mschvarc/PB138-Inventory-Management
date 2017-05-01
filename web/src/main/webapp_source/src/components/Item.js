import React, { Component } from 'react';
import {Link} from 'react-router';

class Item extends Component {
	render() {

		var items = this.props.route.items;
		var categories = this.props.route.categories;
		var item = items.find(item => {return item.id === parseInt(this.props.params.item, 10)});
		var item_category = categories.find(category => { return category.name === item.category; });

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
					<dd><Link to={"category/"+item_category.id}>{item_category.name}</Link></dd>
        	<dt>Alert threshold</dt>
					<dd><input type="number" min="-1" value={item.alertThreshold} /></dd>
				</dl>
      </div>
    </div>
	}
}

export default Item;
