package pb138.service.overview;

import java.util.List;

public interface OverviewXmlExporter {

    String exportToXml(List<OverviewResult> results);

}
