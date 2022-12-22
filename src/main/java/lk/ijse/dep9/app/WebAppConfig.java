package lk.ijse.dep9.app;

import lk.ijse.dep9.app.api.filter.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@ComponentScan
@EnableWebMvc
public class WebAppConfig {

}
