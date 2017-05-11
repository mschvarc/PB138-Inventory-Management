import soap from 'browser-soap';

var sopaApiUrl = 'http://localhost:8080/services/soap?wsdl';

export default class Data {

  constructor(app) {
    this.app = app;
    this.client = undefined;

    soap.createClient(sopaApiUrl, (err, client) => {
      if(err) {
        this.app.setState({error: "Cannot connect to SOAP api"});
      } else {
        this.client = client;

        // this.client.addTestData((err, result) => {
        //   if(err) {
        //     this.app.setState({error: "Cannot add items"});
        //   } else {
        //     console.log(">>>TEST ITEMS ADDED<<<");
        //   }
        // });

        this.loadItems();
        this.loadCategories();
      }
    });
  }

  loadItems() {
    this.client.getAllItems((err, result) => {
      if(err) {
        this.app.setState({error: "Cannot retrive items"});
      } else {
        console.log("items", this.resultArray(result.return.item));
        this.app.setState({items: this.resultArray(result.return.item)});
      }
    });
  }

  loadCategories() {
    this.client.getAllCategories((err, result) => {
      if(err) {
        this.app.setState({error: "Cannot retrive categories"});
      } else {
        console.log("categories", this.resultArray(result.return.item));
        this.app.setState({categories: this.resultArray(result.return.item)});
      }
    });
  }

  loadSales(timeUnit, entityType, entityId, dateStart, numberOfTimeUnit) {
    var fname = "get"+timeUnit+"SalesFor"+entityType;

    this.client[fname]({arg0: entityId, arg1: dateStart, arg2: numberOfTimeUnit}, (err, result) => {
      if(err) {
        this.app.setState({error: "Cannot retrive sales"});
      } else {
        console.log("sales", result);
        this.app.setState({sales: this.resultArray(result.return.item)});
      }
    });
  }

  import(xmlToImport) {
    this.client.importXml({arg0: xmlToImport}, (err, result) => {
      if(err) {
        this.app.setState({importState: "Cannot import XML file: "+(result.Body ? result.Body.Fault.faultstring : err)});
      } else {
        this.app.setState({importState: "Import successfull"});
      }
    });
  }

  export(callback) {
    this.client.exportAllItemsToXml((err, result) => {
      if(err) {
        this.app.setState({exportState: "Cannot export XML file: "+(result.Body ? result.Body.Fault.faultstring : err)});
      } else {
        this.app.setState({exportState: "Export successfull"});
        callback(result.return);
      }
    });
  }

  changeItem(ean, currentCount, unit, alertThreshold) {
    this.client.changeItem({arg0: ean, arg1: currentCount, arg2: unit, arg3: alertThreshold}, (err, result) => {
      if(err) {
        this.app.setState({error: "Cannot update item! "+(result.Body ? result.Body.Fault.faultstring : err)});
      } else {
        var changedItems = this.app.state.items.map(function(item) {
          if(item.ean === result.return.ean) {
            item.currentCount = result.return.currentCount
            item.unit = result.return.unit
            item.alertThreshold = result.return.alertThreshold
          }
          return item;
        });
        this.app.setState({items: changedItems});
      }
    });
  }

  resultArray(data) {
    //0
    if(!data) {
      return [];
    }
    //1
    if(!Array.isArray(data)) {
      return [data];
    }
    //2+
    return data;
  }

}
