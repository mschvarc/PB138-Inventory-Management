package pb138.web;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pb138.web.controllers.SoapBean;

import javax.xml.ws.Endpoint;

@SpringBootApplication
@ComponentScan(basePackages = "pb138.web.controllers")
@ImportResource(locations = {"classpath*:META-INF/persistence-config.xml"})
@EnableTransactionManagement
public class WebApplication {

    @Autowired
    private SoapBean soapBean;
    @Autowired
    private Bus bus;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, soapBean);
        //endpoint.setAddress("/soap");
        endpoint.publish("/soap");
        return endpoint;
    }
}