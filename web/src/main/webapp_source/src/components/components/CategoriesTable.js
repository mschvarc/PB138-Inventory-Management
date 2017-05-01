import React, { Component } from 'react';
import {Link} from 'react-router';

class CategoriesTable extends Component {
	render() {

		var categories = this.props.categories;

    return <table className="dashboard-table">
      <colgroup>
        <col width="80" />
        <col width="200" />
      </colgroup>
      <thead>
        <tr>
          <th><a href="#">Id<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Name<i className="fa fa-caret-down"></i></a></th>
        </tr>
      </thead>
      <tbody>
      {categories.map(function(category, i){
        return <tr key={i}>
          <td>
            {category.id}
          </td>
          <td className="bold"><Link to={"category/"+category.id}>{category.name}</Link></td>
        </tr>
      })}
      </tbody>
    </table>
  }
}

export default CategoriesTable;
