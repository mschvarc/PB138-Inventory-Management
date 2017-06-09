---
layout: default
---

# PB138 Inventory Management System

## [Project Wiki](https://github.com/mschvarc/PB138-Inventory-Management/wiki)

## [JavaDoc](https://mschvarc.github.io/PB138-Inventory-Management/javadoc/apidocs/)

## Project Description:
Create a web application, which will allow a shop owner to keep track of the current inventory by adding new shipments and past sales from XML files. It will also allow to manually set the current inventory count for the items. The system will allow the following operations from a web interface:
* Provide daily/weekly/monthly overview of sales by category or for individual items. This overview will be exportable as XML;
* Show the current individual item count;
* Send alerts when stock gets below a certain threshold defined per item;
* Allow importing of received shipments from an XML format;
* Allow exporting of current inventory in XML format;
* Support importing shipments and sales via a SOAP API;


## Class Diagram:
[Full size](https://mschvarc.github.io/PB138-Inventory-Management/images/ClassDiagram1.png)


![](https://mschvarc.github.io/PB138-Inventory-Management/images/ClassDiagram1.png)

## Use Case Diagram:
[Full size](https://mschvarc.github.io/PB138-Inventory-Management/images/UseCaseDiagram1.png)

![](https://mschvarc.github.io/PB138-Inventory-Management/images/UseCaseDiagram1.png)

## Docbook reports:
* [Dominik Gmiterko](https://mschvarc.github.io/PB138-Inventory-Management/docbook/dominik_gmiterko.html)
* [Jan Čech](https://mschvarc.github.io/PB138-Inventory-Management/docbook/jan_cech.html)
* [Markéta Elederová](https://mschvarc.github.io/PB138-Inventory-Management/docbook/elederova_marketa.html)
* [Martin Schvarcbacher](https://mschvarc.github.io/PB138-Inventory-Management/docbook/martin_schvarcbacher.html)


# SOAP API Documentation
For detailed documentation, please refer to the JavaDoc of pb138.web.controllers.SoapBean. To use this API follow the WSDL requirements.
* [SOAP API JavaDoc, go to pb138.web.controllers.SoapBean](https://mschvarc.github.io/PB138-Inventory-Management/javadoc/apidocs/)
* [SOAP WSDL file](https://mschvarc.github.io/PB138-Inventory-Management/soap/soap.xml)
