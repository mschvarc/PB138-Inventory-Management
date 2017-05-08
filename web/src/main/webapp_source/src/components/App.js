import React, { Component } from 'react';
import { Router, Route, IndexRoute, hashHistory } from 'react-router';

import Data from '../services/Data';

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
		window.app = this;

		this.state = {
		       error: undefined,
		       items: [],
		       categories: []
		};
		this.data = new Data(this);
	}

	render() {
		if(this.state.error) {

			var children = <div className="page-not-found row">
						<div className="small-12 columns">
			      	<h2>Error!</h2>
			        <p>{this.state.error}</p>
			        <ul>
			        	<li><a href="/">Reload</a></li>
			        </ul>
						</div>
			    </div>
			return <Layout children={children} />

		} else {

			return <Router history={hashHistory}>
						<Route path="/" component={Layout}>
							<IndexRoute component={routeProps => <Home items={this.state.items} categories={this.state.categories} />} />
							<Route path="inventory" component={routeProps => <Inventory items={this.state.items} categories={this.state.categories} />} />
							<Route path="item/:item" component={routeProps => <Item items={this.state.items} categories={this.state.categories} paramItem={routeProps.params.item} />} />
							<Route path="categories" component={routeProps => <Categories categories={this.state.categories} />} />
							<Route path="category/:category" component={routeProps => <Category items={this.state.items} categories={this.state.categories} paramCategory={routeProps.params.category} />} />
							<Route path="import-export" component={routeProps => <ImportExport />} />
							<Route path="sales" component={routeProps => <Sales />} />
							<Route path="*" component={routeProps => <NotFound />} />
						</Route>
					</Router>

		}
	}
}

export default App;
