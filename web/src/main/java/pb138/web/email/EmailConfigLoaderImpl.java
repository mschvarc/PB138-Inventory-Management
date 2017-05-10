package pb138.web.email;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * {@inheritDoc}
 */
@Component
public class EmailConfigLoaderImpl implements EmailConfigLoader {

    /**
     * {@inheritDoc}
     */
    public Map<String, String> extractEmailConfig() {
        //Source: PB138 homework1, XPath
        try {
            Map<String, String> map = new HashMap<>();
            URL emailConfig = getClass().getClassLoader().getResource("emailconfig.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(emailConfig.toString());

            final XPath xpath = XPathFactory.newInstance().newXPath();

            final String username = "/config/email/username/text()";
            map.put("username", (String) xpath.evaluate(username, doc, XPathConstants.STRING));

            final String password = "/config/email/password/text()";
            map.put("password", (String) xpath.evaluate(password, doc, XPathConstants.STRING));

            final String host = "/config/email/host/text()";
            map.put("host", (String) xpath.evaluate(host, doc, XPathConstants.STRING));

            final String domain = "/config/email/domain/text()";
            map.put("domain", (String) xpath.evaluate(domain, doc, XPathConstants.STRING));

            final String from = "/config/email/from/text()";
            map.put("from", (String) xpath.evaluate(from, doc, XPathConstants.STRING));

            final String to = "/config/email/to/text()";
            map.put("to", (String) xpath.evaluate(to, doc, XPathConstants.STRING));

            final String subject = "/config/email/subject/text()";
            map.put("subject", (String) xpath.evaluate(subject, doc, XPathConstants.STRING));

            final String encoding = "/config/email/encoding/text()";
            map.put("encoding", (String) xpath.evaluate(encoding, doc, XPathConstants.STRING));

            final String enabled = "/config/email/enabled/text()";
            map.put("enabled", (String) xpath.evaluate(enabled, doc, XPathConstants.STRING));

            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
