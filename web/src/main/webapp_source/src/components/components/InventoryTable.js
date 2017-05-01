import React, { Component } from 'react';
import {Link} from 'react-router';

class InventoryTable extends Component {
	render() {

		var items = this.props.items;
		var categories = this.props.categories;

    return <table className="dashboard-table">
      <colgroup>
        <col width="80" />
        <col width="200" />
        <col width="100" />
        <col width="100" />
        <col width="200" />
      </colgroup>
      <thead>
        <tr>
          <th><a href="#">Id<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Name<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Current<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Status<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Category<i className="fa fa-caret-down"></i></a></th>
        </tr>
      </thead>
      <tbody>
      {items.map(function(item, i){
        var item_status = <span className="label success">&#x2714;</span>
        if (item.currentCount === item.alertThreshold){
          item_status = <span className="label warning">&#x2715;</span>
        } else if (item.currentCount < item.alertThreshold){
          item_status = <span className="label alert">&#x2715;</span>
        }

        var item_category = categories.find(category => { return category.name === item.category; });

        return <tr key={i}>
          <td>
            {item.id}
          </td>
          <td className="bold"><Link to={"item/"+item.id}>{item.name}</Link></td>
          <td>{item.currentCount} {item.unit}</td>
          <td>{item_status}</td>
          <td><Link to={"category/"+item_category.id}>{item_category.name}</Link></td>
        </tr>
      })}
      </tbody>
    </table>
  }
}

export default InventoryTable;
