package lk.ijse.dep9.app;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.annotation.RequestScope;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
public class WebRootConfig {

    @Bean
    public JndiObjectFactoryBean dataSource(){
        JndiObjectFactoryBean jndiObjFBean = new JndiObjectFactoryBean();
        jndiObjFBean.setJndiName("java:comp/env/jdbc/task-app");
        jndiObjFBean.setExpectedType(DataSource.class);
        return jndiObjFBean;
    }
    @Bean
    @RequestScope
    public Connection connection(DataSource dataSource){
        return DataSourceUtils.getConnection(dataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource src){
        return new DataSourceTransactionManager(src);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
