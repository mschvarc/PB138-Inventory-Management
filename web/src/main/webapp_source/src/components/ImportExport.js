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

		var importState = this.props.importState ? <div className="callout warning">{this.props.importState}</div> : null;
		var exportState = this.props.exportState ? <div className="callout warning">{this.props.exportState}</div> : null;

		var importAreaStyle = {
			width: '100%',
      	borderWidth: 3,
      	borderColor: '#888',
      	color: '#888',
      	borderStyle: 'dashed',
			borderRadius: 16,
			textAlign: 'center',
			padding: '4em',
			cursor: 'pointer'
		}

		return <div className="page-import-export row">
      <div className="small-12 medium-6 columns">
				<h2>Import</h2>
				<p>You can import all 4 types of XML files by dropping them here. Documentation of files can be found on <a href="https://github.com/mschvarc/PB138-Inventory-Management/wiki/XML-Data-format">wiki</a></p>
				<Dropzone style={importAreaStyle} multiple={false} onDrop={this.onImport.bind(this)}>
          <div>Try dropping some files here, or click to select files to upload.</div>
        </Dropzone>
				{importState}
			</div>
			<div className="small-12 medium-6 columns">
				<h2>Export</h2>
				<p>This function will allow you to download actual state of your inventory.</p>
				<a className="button large expanded success" onClick={this.onExport.bind(this)}>Download</a>
				{exportState}
			</div>
    </div>
	}
}

export default ImportExport;
