import React, { Component } from 'react';
import CategoriesTable from './components/CategoriesTable';

class Categories extends Component {
	render() {

		var categories = this.props.route.categories;

		return <div className="page-categories row">
      <div className="small-12 columns">
        <h2>Categories</h2>

				<CategoriesTable categories={categories} />

      </div>
    </div>
	}
}

export default Categories;
