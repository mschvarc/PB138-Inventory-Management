import React, { Component } from 'react';
import {Link} from 'react-router';

class Item extends Component {

	componentWillMount() {
		this.loadItem();
	}

	changeItem() {
		this.props.changeItem(this.state.ean, this.state.currentCount, this.state.unit, this.state.alertThreshold);
	}

	loadItem() {
		this.item = this.props.items.find(item => {return item.ean === this.props.paramItem});

		if(this.item) {
			this.setState({
				ean: this.item.ean,
				currentCount: this.item.currentCount,
				unit: this.item.unit,
				alertThreshold: this.item.alertThreshold
			});
		}
	}

	onCurrentCount(e) {
		this.setState({
			currentCount: e.currentTarget.value
		});
	}

	onUnit(e) {
		this.setState({
			unit: e.currentTarget.value
		});
	}

	onAlertThreshold(e) {
		this.setState({
			alertThreshold: e.currentTarget.value
		});
	}

	render() {

		if(!this.item) {
			this.loadItem();
		}

		if(this.item) {
			var item = this.item;

			return <div className="page-item row">
	      <div className="small-12 columns">
	        <h2>Item {item.name} <small>Id: {item.id} Ean: {item.ean}</small></h2>
					<form onSubmit={this.changeItem.bind(this)}>
						<dl>
							<dt>Description</dt>
							<dd>{item.description}</dd>
							<hr/>
							<dt>Current amount</dt>
							<dd>
								<div className="row">
									<div className="small-2 columns"><input type="number" min="0" value={this.state.currentCount} onChange={this.onCurrentCount.bind(this)} /></div>
									<div className="small-10 columns"><input value={this.state.unit} onChange={this.onUnit.bind(this)} /></div>
								</div>
							</dd>
		        	<dt>Category</dt>
							<dd><Link to={"category/"+item.category.id}>{item.category.name}</Link></dd>
		        	<dt>Alert threshold</dt>
							<dd><input type="number" min="-1" value={this.state.alertThreshold} onChange={this.onAlertThreshold.bind(this)} /></dd>
						</dl>
						<input type="submit" value="Update" />
					</form>
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
