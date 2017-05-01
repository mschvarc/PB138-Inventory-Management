import React, { Component } from 'react';
import { Router, Route, IndexRoute, hashHistory } from 'react-router';

import Layout from './Layout';
import Home from './Home';
import Inventory from './Inventory';
import Item from './Item';
import Categories from './Categories';
import Category from './Category';
import ImportExport from './ImportExport';
import Sales from './Sales';
import NotFound from './NotFound';

export class App extends Component {

	constructor() {
		super();

		this.state = require("../testingData.json");
	}

	render() {
		return <Router history={hashHistory}>
					<Route path="/" component={Layout} >
						<IndexRoute component={Home} items={this.state.items} categories={this.state.categories}/>
						<Route path="inventory" component={Inventory} items={this.state.items} categories={this.state.categories} />
						<Route path="item/:item" component={Item} items={this.state.items} categories={this.state.categories} />
						<Route path="categories" component={Categories} categories={this.state.categories} />
						<Route path="category/:category" component={Category} items={this.state.items} categories={this.state.categories} />
						<Route path="import-export" component={ImportExport} />
						<Route path="sales" component={Sales} />
						<Route path="*" component={NotFound} />
					</Route>
				</Router>
	}
}

export default App;
