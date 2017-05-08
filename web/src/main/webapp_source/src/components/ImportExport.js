import React, { Component } from 'react';
import Dropzone from 'react-dropzone';

class ImportExport extends Component {

	onImport(files) {
		console.log("Import");
		console.log(files);
	}

	onExport() {
		console.log("Export");
	}

	render() {
		return <div className="page-import-export row">
      <div className="small-12 medium-6 columns">
				<h2>Import</h2>
				<p>You can import all 4 types of XML files by dropping them here. Documentation of files can be found on <a href="https://github.com/mschvarc/PB138-Inventory-Management/wiki/XML-Data-format">wiki</a></p>
				<Dropzone onDrop={this.onImport.bind(this)}>
          <div>Try dropping some files here, or click to select files to upload.</div>
        </Dropzone>

			</div>
			<div className="small-12 medium-6 columns">
				<h2>Export</h2>
				<p>This function will allow you to download actual state of inventory.</p>
				<a className="button" onClick={this.onExport.bind(this)}>Download</a>
			</div>
    </div>
	}
}

export default ImportExport;
