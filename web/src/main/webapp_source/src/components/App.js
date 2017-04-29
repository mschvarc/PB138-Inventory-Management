import React, { Component } from 'react';
import { Router, Route, IndexRoute, hashHistory } from 'react-router';

import Layout from './Layout';
import Home from './Home';
import Inventory from './Inventory';
import Item from './Item';
import Categories from './Categories';
import ImportExport from './ImportExport';
import Sales from './Sales';
import NotFound from './NotFound';

export class App extends Component {

	constructor() {
		super();

		this.state = {};
	}

	render() {
		return <Router history={hashHistory}>
					<Route path="/" component={Layout} >
						<IndexRoute component={Home}/>
						<Route path="inventory" component={Inventory} />
						<Route path="item/:item" component={Item} />
						<Route path="categories" component={Categories} />
						<Route path="import-export" component={ImportExport} />
						<Route path="sales" component={Sales} />
						<Route path="*" component={NotFound} />
					</Route>
				</Router>
	}
}

export default App;
