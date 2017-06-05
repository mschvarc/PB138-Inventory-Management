package pb138.web;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pb138.service.services.EmailSender;
import pb138.service.services.ItemCountWatchdogService;
import pb138.service.services.ItemCountWatchdogServiceImpl;
import pb138.service.services.ItemService;
import pb138.web.controllers.DebugSoapBean;
import pb138.web.controllers.SoapBean;
import pb138.web.email.EmailConfigLoader;
import pb138.web.email.EmailScheduler;
import pb138.web.email.EmailSenderImpl;

import javax.xml.ws.Endpoint;
import java.util.Map;

/**
 * Spring configuration class for web backend
 *
 * @author Martin Schvarcbacher
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableAutoConfiguration
@ComponentScan(basePackages = {"pb138.web.email"})
public class Config {
    @Autowired
    private SoapBean soapBean;

    @Autowired
    private DebugSoapBean debugSoapBean;

    @Autowired
    private Bus bus;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EmailConfigLoader emailConfigLoader;


    /**
     * Bootstraps ItemCountWatchdogService
     *
     * @return ItemCountWatchdogService
     */
    @Bean
    public ItemCountWatchdogService itemCountWatchdogService() {
        return new ItemCountWatchdogServiceImpl(itemService, emailSender());
    }


    /**
     * Bootstraps Endpoint
     *
     * @return Endpoint
     */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, soapBean);
        //endpoint.setAddress("/soap");
        endpoint.publish("/soap");
        return endpoint;
    }

    //TODO: REMOVE FOR RELEASE!!!!

    /**
     * Bootstraps Endpoint DEBUG ONLY
     *
     * @return Endpoint
     */
    @Bean
    public Endpoint endpointDebug() {
        EndpointImpl endpoint = new EndpointImpl(bus, debugSoapBean);
        endpoint.publish("/debugsoap");
        return endpoint;
    }


    /**
     * Bootstraps EmailSender
     *
     * @return EmailSender
     */
    @Bean
    public EmailSender scheduler() {
        EmailSender emailSender = new EmailSenderImpl(javaMailSender(), simpleMailMessage());
        return emailSender;
    }


    /**
     * Bootstraps EmailScheduler
     *
     * @return EmailScheduler
     */
    @Bean
    public EmailScheduler emailScheduler() {
        return new EmailScheduler(itemCountWatchdogService(), emailConfigLoader);
    }

    /**
     * Bootstraps EmailSender
     *
     * @return EmailSender
     */
    @Bean
    public EmailSender emailSender() {
        return new EmailSenderImpl(javaMailSender(), simpleMailMessage());
    }

    /**
     * Configures email sender bean
     *
     * @return JavaMailSender
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        Map<String, String> config = emailConfigLoader.extractEmailConfig();
        javaMailSender.setUsername(config.get("username"));
        javaMailSender.setPassword(config.get("password"));
        javaMailSender.setHost(config.get("host"));
        javaMailSender.setDefaultEncoding(config.get("encoding"));
        return javaMailSender;
    }

    /**
     * Boostraps SimpleMailMessage
     *
     * @return SimpleMailMessage
     */
    @Bean
    public SimpleMailMessage simpleMailMessage() {
        Map<String, String> config = emailConfigLoader.extractEmailConfig();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        assert config != null;
        simpleMailMessage.setFrom(config.get("from"));
        simpleMailMessage.setTo(config.get("to"));
        simpleMailMessage.setSubject(config.get("subject"));
        return simpleMailMessage;
    }
}
