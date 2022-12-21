package lk.ijse.dep9.app;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.web.context.annotation.RequestScope;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class WebRootConfig {

    @Bean
    public JndiObjectFactoryBean dataSource(){
        JndiObjectFactoryBean jndiObjFBean = new JndiObjectFactoryBean();
        jndiObjFBean.setJndiName("java:comp/env/jdbc/task-app");
        jndiObjFBean.setExpectedType(DataSource.class);
        return jndiObjFBean;
    }
    @Bean(destroyMethod = "close")
    @RequestScope
    public Connection connection(DataSource dataSource) throws NamingException, SQLException {

        return dataSource.getConnection();

    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
