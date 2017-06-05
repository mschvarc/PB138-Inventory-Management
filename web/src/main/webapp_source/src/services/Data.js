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
        this.app.setState({items: this.resultArray(result.return.item)});
      }
    });
  }

  loadCategories() {
    this.client.getAllCategories((err, result) => {
      if(err) {
        this.app.setState({error: "Cannot retrive categories"});
      } else {
        this.app.setState({categories: this.resultArray(result.return.item)});
      }
    });
  }

  loadSales(timeUnit, entityType, entityId, dateStart, numberOfTimeUnit) {
    var fname = "get"+timeUnit+"SalesFor"+entityType+"Xml";

    var args = null;

    switch (entityType+timeUnit) {
      case 'ItemDaily':
        args = {
          'ean': entityId,
          'dayStart': dateStart,
          'numberOfDays': numberOfTimeUnit
        }
        break;
      case 'ItemWeekly':
        args = {
          'ean': entityId,
          'weekStart': dateStart,
          'numberOfWeeks': numberOfTimeUnit
        }
        break;
      case 'ItemMonthly':
        args = {
          'ean': entityId,
          'monthStart': dateStart,
          'numberOfMonths': numberOfTimeUnit
        }
        break;
      case 'CategoryDaily':
        args = {
          'category': entityId,
          'dayStart': dateStart,
          'numberOfDays': numberOfTimeUnit
        }
        break;
      case 'CategoryWeekly':
        args = {
          'category': entityId,
          'weekStart': dateStart,
          'numberOfWeeks': numberOfTimeUnit
        }
        break;
      case 'CategoryMonthly':
        args = {
          'category': entityId,
          'monthStart': dateStart,
          'numberOfMonths': numberOfTimeUnit
        }
        break;
      default:
        throw new Error("Illegal entity type or time unit");
    }

    this.client[fname](args, (err, result) => {
      if(err) {
        this.app.setState({sales: "Cannot retrive sales: "+(result.Body ? result.Body.Fault.faultstring : err)});
      } else {
        this.app.setState({sales: this.resultXml(result.return)});
      }
    });
  }

  import(xmlToImport) {
    this.client.importXml({xmlToImport: xmlToImport}, (err, result) => {
      if(err) {
        this.app.setState({importState: "Cannot import XML file: "+(result.Body ? result.Body.Fault.faultstring : err)});
      } else {
        this.app.setState({importState: "Import successfull"});
        this.loadItems();
        this.loadCategories();
      }
    });
  }

  export(callback) {
    this.client.exportAllItemsToXml((err, result) => {
      if(err) {
        this.app.setState({exportState: "Cannot export XML file: "+(result.Body ? result.Body.Fault.faultstring : err)});
      } else {
        this.app.setState({exportState: "Export successfull"});
        callback(this.resultXml(result.return));
      }
    });
  }

  changeItem(ean, currentCount, unit, alertThreshold) {
    this.client.changeItem({ean: ean, currentCount: currentCount, unit: unit, alertThreshold: alertThreshold}, (err, result) => {
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

  resultXml(data) {
    //Windows NUL character bug
    return data.replace(/\0/g, '');
  }

}
