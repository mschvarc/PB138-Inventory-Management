package pb138.service.overview;

import java.util.List;
import pb138.service.exceptions.XmlValidationException;

/**
 * Exporter of overview results to xml.
 */
public interface OverviewXmlExporter {

    /**
     * Export overview results for category to xml.
     *
     * @param results - list of OverviewResultCategory
     * @return xml with category overviews
     * @throws XmlValidationException when occurs some error in generating xml
     */
    String exportCategoryResultToXml(List<OverviewResultCategory> results) throws XmlValidationException;

    /**
     * Export overview results for item to xml.
     *
     * @param results - list of OverviewResultItem
     * @return xml with item overviews
     * @throws XmlValidationException when occurs some error in generating xml
     */
    String exportItemResultToXml(List<OverviewResultItem> results) throws XmlValidationException;

}
