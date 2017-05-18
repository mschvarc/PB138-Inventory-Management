package pb138.service.overview;

import java.io.StringWriter;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pb138.service.exceptions.XmlValidationException;
import pb138.service.xmlvalidator.XmlValidator;

/**
 * Implementation of OverviewXmlExporter interface.
 *
 * @author Marketa Elederova
 */
public class OverviewXmlExporterImpl implements OverviewXmlExporter {

    private final XmlValidator xmlValidator;

    public OverviewXmlExporterImpl(XmlValidator xmlValidator) {
        this.xmlValidator = xmlValidator;
    }

    @Override
    public String exportCategoryResultToXml(List<OverviewResultCategory> results)
            throws XmlValidationException {

        try {
            Document doc = createCategoryOverviewXml(results);
            String xmlExport = documentToString(doc);
            xmlValidator.validate(xmlExport, getClass().getClassLoader().getResource("xml_schema/category_overview_xml_schema.xsd"));

            return xmlExport;

        } catch (TransformerException | ParserConfigurationException ex) {
            throw new XmlValidationException("Exception occurred in XML generation: " + ex.getMessage(), ex);
        }
    }

    @Override
    public String exportItemResultToXml(List<OverviewResultItem> results)
            throws XmlValidationException {

        try {
            Document doc = createItemOverviewXml(results);
            String xmlExport = documentToString(doc);
            xmlValidator.validate(xmlExport, getClass().getClassLoader().getResource("xml_schema/item_overview_xml_schema.xsd"));

            return xmlExport;

        } catch (TransformerException | ParserConfigurationException ex) {
            throw new XmlValidationException("Exception occurred in XML generation: " + ex.getMessage(), ex);
        }
    }

    private Document createItemOverviewXml(List<OverviewResultItem> results)
            throws ParserConfigurationException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();

        //<itemOverview>
        Element itemOverviewElement = doc.createElement("itemOverview");
        doc.appendChild(itemOverviewElement);

        for (OverviewResultItem resultItem : results) {

            //-   <overview>
            Element overviewElement = doc.createElement("overview");

            //-   -   <timespan>
            Element timespanElement = doc.createElement("timespan");
            timespanElement.appendChild(doc.createTextNode(getPeriodString(resultItem.getTimespan())));
            overviewElement.appendChild(timespanElement);

            //-   -   <dateStart>
            Element dateStartElement = doc.createElement("dateStart");
            dateStartElement.appendChild(doc.createTextNode(getDateString(resultItem.getStartDate())));
            overviewElement.appendChild(dateStartElement);

            //-   -   <item>
            Element itemElement = doc.createElement("item");

            //-   -   -   <ean>
            Element eanElement = doc.createElement("ean");
            eanElement.appendChild(doc.createTextNode(String.format("%1$013d", resultItem.getItem().getEan())));
            itemElement.appendChild(eanElement);

            //-   -   -   <name>
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(resultItem.getItem().getName()));
            itemElement.appendChild(nameElement);

            //-   -   -   <unit>
            Element unitElement = doc.createElement("unit");
            unitElement.appendChild(doc.createTextNode(resultItem.getItem().getUnit()));
            itemElement.appendChild(unitElement);

            //-   -   -   <description>
            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(resultItem.getItem().getDescription()));
            itemElement.appendChild(descriptionElement);

            //-   -   -   <categoryName>
            Element categoryNameElement = doc.createElement("categoryName");
            categoryNameElement.appendChild(doc.createTextNode(resultItem.getItem().getCategory().getName()));
            itemElement.appendChild(categoryNameElement);

            //-   -   -   <alertThreshold>
            if (resultItem.getItem().getAlertThreshold() != null) {
                Element alertThresholdElement = doc.createElement("alertThreshold");
                alertThresholdElement.appendChild(doc.createTextNode(resultItem.getItem().getAlertThreshold().toString()));
                itemElement.appendChild(alertThresholdElement);
            }

            overviewElement.appendChild(itemElement);

            //-   -   <countSold>
            Element countSoldElement = doc.createElement("countSold");
            countSoldElement.appendChild(doc.createTextNode(Integer.toString(resultItem.getEntityCount())));
            overviewElement.appendChild(countSoldElement);

            itemOverviewElement.appendChild(overviewElement);
        }

        return doc;
    }

    private Document createCategoryOverviewXml(List<OverviewResultCategory> results)
            throws ParserConfigurationException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();

        //<categoryOverview>
        Element categoryOverviewElement = doc.createElement("categoryOverview");
        doc.appendChild(categoryOverviewElement);

        for (OverviewResultCategory resultCategory : results) {

            //-   <overview>
            Element overviewElement = doc.createElement("overview");

            //-   -   <timespan>
            Element timespanElement = doc.createElement("timespan");
            timespanElement.appendChild(doc.createTextNode(getPeriodString(resultCategory.getTimespan())));
            overviewElement.appendChild(timespanElement);

            //-   -   <dateStart>
            Element dateStartElement = doc.createElement("dateStart");
            dateStartElement.appendChild(doc.createTextNode(getDateString(resultCategory.getStartDate())));
            overviewElement.appendChild(dateStartElement);

            //-   -   <category>
            Element categoryElement = doc.createElement("category");

            //-   -   -   <name>
            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(resultCategory.getCategory().getName()));
            categoryElement.appendChild(nameElement);

            //-   -   -   <description>
            Element descriptionElement = doc.createElement("description");
            descriptionElement.appendChild(doc.createTextNode(resultCategory.getCategory().getDescription()));
            categoryElement.appendChild(descriptionElement);

            overviewElement.appendChild(categoryElement);

            //-   -   <countSold>
            Element countSoldElement = doc.createElement("countSold");
            countSoldElement.appendChild(doc.createTextNode(Integer.toString(resultCategory.getEntityCount())));
            overviewElement.appendChild(countSoldElement);

            categoryOverviewElement.appendChild(overviewElement);
        }

        return doc;
    }

    private String getPeriodString(Period period) {

        if (period.getDays() == 1) {
            return "day";
        }
        if (period.getDays() == 7) {
            return "week";
        }
        if (period.getMonths() == 1) {
            return "month";
        }

        throw new IllegalStateException("Wrong period");
    }

    private String getDateString(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.format("%1$tY-%1$tm-%1$td", calendar);
    }

    private String documentToString(Document doc) throws TransformerException {

        StringWriter sw = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.reset();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        String xmlExport = sw.toString();

        return xmlExport;
    }

}
