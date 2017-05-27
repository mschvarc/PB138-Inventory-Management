import React, { Component } from 'react';
import xslt from 'xslt';

import SalesForm from './components/SalesForm';

var xsltTemplate = require('./sales_preview_template.xsl');

class Sales extends Component {

	render() {

		var salesXml = this.props.sales;
		var salesXmlPreview = null;
		if(this.props.sales && this.props.sales.startsWith("<?xml")) {
			try {
				salesXmlPreview = xslt(this.props.sales, xsltTemplate);
			} catch (e) {
				console.log("Error while creating xml preview! ", e);
			}
		}

		return <div className="page-sales row">
      <div className="small-12 columns">
        <h2>Sales</h2>
				<SalesForm loadSales={this.props.loadSales} />
				{salesXmlPreview ? <div><h3>Preview</h3><div dangerouslySetInnerHTML={{__html: salesXmlPreview}}></div></div> : null}
				<h3>Raw data</h3>
				<pre>{salesXml}</pre>
      </div>
    </div>
	}
}

export default Sales;
