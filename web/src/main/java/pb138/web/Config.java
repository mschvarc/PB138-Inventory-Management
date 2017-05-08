package pb138.web;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pb138.web.controllers.SoapBean;

import javax.xml.ws.Endpoint;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
@EnableAutoConfiguration
public class Config {
    @Autowired
    private SoapBean soapBean;

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, soapBean);
        //endpoint.setAddress("/soap");
        endpoint.publish("/soap");
        return endpoint;
    }
}
