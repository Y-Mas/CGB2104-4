package cn.tedu.storage;

import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/*
自定义的数据源自动配置类
用来创建AT 事务的数据源代理对象
 */
@Configuration
public class DSAutoConfiguration {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new HikariDataSource();
    }

    @Primary   //首选对象
    @Bean
    public DataSource dataSourceProxy(DataSource ds){
        return new DataSourceProxy(ds);
    }


}
