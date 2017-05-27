import React, { Component } from 'react';
import xslt from 'xslt';

import SalesForm from './components/SalesForm';

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

var xsltTemplate = `<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>

	 <xsl:template match="itemOverview">
		 <table class="dashboard-table">
		   <tbody>
			   <xsl:apply-templates select="overview"/>
		   </tbody>
		 </table>
	 </xsl:template>
	 <xsl:template match="itemOverview/overview">
		 <tr>
			 <td><xsl:value-of select="dateStart" /></td>
			 <td><xsl:value-of select="item/name" /></td>
			 <td><xsl:value-of select="countSold" /><xsl:text> </xsl:text><xsl:value-of select="item/unit" /></td>
			 <td><small><xsl:value-of select="item/ean" /></small></td>
		 </tr>
	 </xsl:template>

	 <xsl:template match="categoryOverview">
		 <table class="dashboard-table">
		   <tbody>
			   <xsl:apply-templates select="overview"/>
		   </tbody>
		 </table>
	 </xsl:template>
	 <xsl:template match="categoryOverview/overview">
		 <tr>
			 <td><xsl:value-of select="dateStart" /></td>
			 <td><xsl:value-of select="countSold" /></td>
		 </tr>
	 </xsl:template>

</xsl:stylesheet>`;

export default Sales;
