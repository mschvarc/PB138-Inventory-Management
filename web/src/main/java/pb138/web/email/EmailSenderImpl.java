package pb138.web.email;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import pb138.dal.entities.Item;
import pb138.service.services.EmailSender;

import java.util.Date;
import java.util.List;


/**
 * Sends an alert when count gets below a threshold
 * @author Martin Schvarcbacher
 */
public class EmailSenderImpl implements EmailSender {

    private final MailSender mailSender;
    private final SimpleMailMessage templateMessage;

    public EmailSenderImpl(MailSender mailSender, SimpleMailMessage templateMessage) {
        this.mailSender = mailSender;
        this.templateMessage = templateMessage;
    }

    @Override
    public void sendAlerts(List<Item> items) {
        //Source: http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mail.html
        if (items.isEmpty()) {
            System.err.println((new Date()) + " received empty alert collection for emails, returning");
            return;
        }
        //TODO: use an XSLT or something to generate a nice HTML
        StringBuilder builder = new StringBuilder();
        builder.append("Items in critical state:").append(System.lineSeparator());

        for (Item item : items) {
            builder.append(item.getName())
                    .append(" EAN: ")
                    .append(item.getEan())
                    .append(" has count: ")
                    .append(item.getCurrentCount())
                    .append(" which is below the defined minimum of: ")
                    .append(item.getAlertThreshold())
                    .append(System.lineSeparator());
        }

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setText(builder.toString());
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
