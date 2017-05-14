package pb138.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Core Spring Boot Launcher class, responsible for bootstrapping web layer
 * @author Martin Schvarcbacher
 */
@SpringBootApplication
@ComponentScan(basePackages = "pb138.web.controllers")
@ImportResource(locations = {"classpath*:META-INF/persistence-config.xml"})
//@EnableTransactionManagement
@Import(Config.class)
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {

    private static Class<WebApplication> applicationClass = WebApplication.class;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

}