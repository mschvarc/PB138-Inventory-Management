import React, { Component } from 'react';
import {Link} from 'react-router';

class InventoryTable extends Component {
	render() {

		var items = this.props.items;

    return <table className="dashboard-table">
      <colgroup>
        <col width="200" />
        <col width="100" />
        <col width="100" />
        <col width="200" />
      </colgroup>
      <thead>
        <tr>
					<th><a href="#">Name<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Current<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Status<i className="fa fa-caret-down"></i></a></th>
          <th><a href="#">Category<i className="fa fa-caret-down"></i></a></th>
					<th><a href="#">Ean<i className="fa fa-caret-down"></i></a></th>
				</tr>
      </thead>
      <tbody>
      {items.map(function(item, i){
        var item_status = <span className="label success">&#x2714;</span>
        if (item.currentCount === item.alertThreshold){
          item_status = <span className="label warning">&#x2715;</span>
        } else if (parseInt(item.currentCount, 10) < parseInt(item.alertThreshold, 10)){
          item_status = <span className="label alert">&#x2715;</span>
        }

        return <tr key={i}>
          <td className="bold"><Link to={"item/"+item.ean}>{item.name}</Link></td>
          <td>{item.currentCount} {item.unit}</td>
          <td>{item_status}</td>
          <td><Link to={"category/"+item.category.name}>{item.category.name}</Link></td>
					<td>
						<small>{item.ean}</small>
					</td>
				</tr>
      })}
      </tbody>
    </table>
  }
}

export default InventoryTable;
