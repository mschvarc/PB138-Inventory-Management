package pb138.service.XmlImportExport;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import pb138.dal.entities.Shipment;
import pb138.service.exceptions.EntityDoesNotExistException;
import pb138.service.exceptions.ServiceException;
import pb138.service.facades.ShipmentFacade;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Jan on 08.05.2017.
 *
 */
public class ShipmentImporterImpl implements ShipmentImporter {
    private ShipmentFacade shipmentFacade;

    /**
     * Constructor
     * @param shipmentFacade shipment facade
     */
    public ShipmentImporterImpl(ShipmentFacade shipmentFacade) {
        this.shipmentFacade = shipmentFacade;
    }

    @Override
    public void importShipments(Element e) throws EntityDoesNotExistException, ServiceException {
        NodeList shipments = e.getElementsByTagName("shipment");
        List<Shipment> shipmentList = new ArrayList<>();
        for (int i = 0; i < shipments.getLength(); i++) {
            Element shipment = (Element) shipments.item(i);

            NodeList eanNode = shipment.getElementsByTagName("itemEan");
            String eanString = eanNode.item(0).getTextContent();
            long ean = Long.parseLong(eanString);

            NodeList amountNode = shipment.getElementsByTagName("amount");
            String amountString = amountNode.item(0).getTextContent();
            int amount = Integer.parseInt(amountString);

            NodeList dateNode = shipment.getElementsByTagName("date");
            String dateString = dateNode.item(0).getTextContent();
            String[] parsedDate = dateString.split("-");
            Date date = new GregorianCalendar(Integer.parseInt(parsedDate[0]), Integer.parseInt(parsedDate[1]), Integer.parseInt(parsedDate[2])).getTime();
            Shipment s = shipmentFacade.addShipment(ean, date, amount);
            shipmentList.add(s);
        }
        for (Shipment s : shipmentList) {
            shipmentFacade.storeShipmentInDb(s);
        }
    }
}
