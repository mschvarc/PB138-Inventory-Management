import React, { Component } from 'react';
import InventoryTable from './components/InventoryTable';

class Category extends Component {
	render() {

		var categories = this.props.categories;
		var category = categories.find(category => {return category.id === this.props.paramCategory});

    var itemsInCategory = this.props.items.filter(item => item.category.id === category.id);

		if(category) {
			return <div className="page-item row">
	      <div className="small-12 columns">
	        <h2>Category {category.name} <small>Id: {category.id}</small></h2>
					<dl>
						<dt>Description</dt>
						<dd>{category.description}</dd>
						<hr/>
						<dt>Items</dt>
						<dd>

	            <InventoryTable items={itemsInCategory} categories={categories} />

						</dd>
					</dl>
	      </div>
	    </div>
		} else {
			return <div className="page-item row">
				<div className="small-12 columns">
					<h2>Category not found</h2>
				</div>
			</div>
		}
	}
}

export default Category;
