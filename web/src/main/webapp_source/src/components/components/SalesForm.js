import React, { Component } from 'react';
import DateTime from 'react-datetime';
import moment from 'moment';

export default class SalesForm extends Component {

	constructor() {
    super();

		this.state = {
			timeUnit: "Daily",
      entityType: "Item",
      entityId: "",
      dateStart: moment().format("YYYY-MM-DD"),
      numberOfTimeUnit: 1
		};

	}

  onTimeUnitChange(e) {
    this.setState({
      timeUnit: e.currentTarget.value
    });
    this.autoLoadSales();
  }

  onEntityTypeChange(e) {
    this.setState({
      entityType: e.currentTarget.value
    });
    this.autoLoadSales();
  }

  onEntityIdChange(e) {
    this.setState({
      entityId: e.currentTarget.value
    });
    this.autoLoadSales();
  }

  onDateStartChange(e) {
    this.setState({
      dateStart: moment(e).format("YYYY-MM-DD")
    });
    this.autoLoadSales();
  }

  onNumberOfTimeUnitChange(e) {
    this.setState({
      numberOfTimeUnit: e.currentTarget.value
    });
    this.autoLoadSales();
  }

  autoLoadSales() {
    if(!this.state.entityId) {
      return;
    }

		setTimeout(() => {
	    var dateStart = this.state.dateStart + "T00:00:00";
	    this.props.loadSales(this.state.timeUnit, this.state.entityType, this.state.entityId, dateStart, this.state.numberOfTimeUnit);
		});
	}

  render() {
    return <form onSubmit={() => this.props.loadSales()}>
      <div>
        <input type="radio" name="timeUnit" value="Daily" checked={this.state.timeUnit === "Daily"} onChange={this.onTimeUnitChange.bind(this)} /> Daily
        <input type="radio" name="timeUnit" value="Weekly" checked={this.state.timeUnit === "Weekly"} onChange={this.onTimeUnitChange.bind(this)} /> Weekly
        <input type="radio" name="timeUnit" value="Monthly" checked={this.state.timeUnit === "Monthly"} onChange={this.onTimeUnitChange.bind(this)} /> Monthly
      </div>
      <div>
        <input type="radio" name="entityType" value="Item" checked={this.state.entityType === "Item"} onChange={this.onEntityTypeChange.bind(this)} /> Item
        <input type="radio" name="entityType" value="Category" checked={this.state.entityType === "Category"} onChange={this.onEntityTypeChange.bind(this)} /> Category
      </div>
      <div>
        <input type="text" name="entityId" value={this.state.entityId} onChange={this.onEntityIdChange.bind(this)} />
      </div>
      <div>
        <DateTime name="dateStart" dateFormat="YYYY-MM-DD" timeFormat={false} value={this.state.dateStart} onChange={this.onDateStartChange.bind(this)} />
      </div>
      <div>
        <input type="number" name="numberOfTimeUnit" min="1" value={this.state.numberOfTimeUnit} onChange={this.onNumberOfTimeUnitChange.bind(this)} />
      </div>
    </form>
  }
}
