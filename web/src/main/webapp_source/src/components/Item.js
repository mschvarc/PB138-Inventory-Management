import React, { Component } from 'react';

class Item extends Component {
	render() {
		return <div className="page-item row">
      <div className="small-12 columns">
        <h2>{this.props.item.name}</h2>
        <small>Ean: {this.props.item.ean}, Id: {this.props.item.id}</small>
        <p>{this.props.item.description}</p>
        <p>Current amount: {this.props.item.currentCount} {this.props.item.unit}</p>
        <p>Category: {this.props.item.category}</p>
        <p>Alert threshold: {this.props.item.alertThreshold}</p>
      </div>
    </div>
	}
}

export default Item;
