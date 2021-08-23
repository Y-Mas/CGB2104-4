package cn.tedu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
public class DbInitApplication {
    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(DbInitApplication.class, args);
    }
    @PostConstruct
    public void init() throws SQLException {
        exec("sql/account.sql");
        exec("sql/order.sql");
        exec("sql/seata-server.sql");
        exec("sql/storage.sql");
    }

    private void exec(String sql) throws SQLException {
        ClassPathResource cpr = new ClassPathResource(sql, DbInitApplication.class.getClassLoader());
        EncodedResource resource = new EncodedResource(cpr, "UTF-8");

        ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
    }
}
