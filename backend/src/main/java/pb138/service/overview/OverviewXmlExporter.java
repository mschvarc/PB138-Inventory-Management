package pb138.service.overview;

import java.util.List;

public interface OverviewXmlExporter {

    String exportCategoryResultToXml(List<OverviewResultCategory> results);

    String exportItemResultToXml(List<OverviewResultItem> results);


}
