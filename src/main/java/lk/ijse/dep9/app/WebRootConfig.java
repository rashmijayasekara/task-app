package lk.ijse.dep9.app;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;


@Configuration
@ComponentScan
@EnableTransactionManagement
public class WebRootConfig {

    @Bean
    public JndiObjectFactoryBean dataSource(){
        JndiObjectFactoryBean jndiObjFBean = new JndiObjectFactoryBean();
        jndiObjFBean.setJndiName("java:comp/env/jdbc/task-app");
        jndiObjFBean.setExpectedType(DataSource.class);
        return jndiObjFBean;
    }
//    @Bean
//    @RequestScope
//    public Connection connection(DataSource dataSource){
//        return DataSourceUtils.getConnection(dataSource);
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource src){
        return new DataSourceTransactionManager(src);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
