import React, { Component } from 'react';
import InventoryTable from './components/InventoryTable';

class Category extends Component {
	render() {

		var categories = this.props.route.categories;
		var category = categories.find(category => {return category.id === parseInt(this.props.params.category, 10)});

    var itemsInCategory = this.props.route.items.filter(item => item.category === category.name);

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
	}
}

export default Category;
