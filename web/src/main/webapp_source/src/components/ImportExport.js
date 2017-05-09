import React, { Component } from 'react';
import Dropzone from 'react-dropzone';
import fileDownload from 'react-file-download';

class ImportExport extends Component {

	onImport(files) {
		for(var fileIndex in files) {
			var file = files[fileIndex];

			var fileReader = new FileReader();
			fileReader.addEventListener("load", () => {
				// send XML as string to server
				this.props.import(fileReader.result);
		  }, false);
    	fileReader.readAsText(file, 'utf-8');
		}
	}

	onExport() {
		this.props.export(xml => {
			console.log("EXPORT", xml);
			fileDownload(xml, 'export.xml');
		});
	}

	render() {

		var importState = this.props.importState ? <span className="label warning">{this.props.importState}</span> : null;
		var exportState = this.props.exportState ? <span className="label warning">{this.props.exportState}</span> : null;

		return <div className="page-import-export row">
      <div className="small-12 medium-6 columns">
				<h2>Import</h2>
				<p>You can import all 4 types of XML files by dropping them here. Documentation of files can be found on <a href="https://github.com/mschvarc/PB138-Inventory-Management/wiki/XML-Data-format">wiki</a></p>
				<Dropzone multiple={true} onDrop={this.onImport.bind(this)}>
          <div>Try dropping some files here, or click to select files to upload.</div>
        </Dropzone>
				{importState}
			</div>
			<div className="small-12 medium-6 columns">
				<h2>Export</h2>
				<p>This function will allow you to download actual state of your inventory.</p>
				<a className="button" onClick={this.onExport.bind(this)}>Download</a>
			</div>
			{exportState}
    </div>
	}
}

export default ImportExport;
