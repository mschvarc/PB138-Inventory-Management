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
