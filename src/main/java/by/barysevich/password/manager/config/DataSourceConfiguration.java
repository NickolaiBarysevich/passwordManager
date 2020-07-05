package by.barysevich.password.manager.config;

import by.barysevich.password.manager.provider.ParametersStoreProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    /**
     * Dev dataSource bean declaration.
     *
     * @return dataSource
     */
    @Bean
    @Profile("test")
    public DataSource dataSource() {
        return createDataSource("postgres", "bmUYo4s3!","jdbc:postgresql://localhost/pvault");
    }

    private DataSource createDataSource(String username, String password, String url) {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setJdbcUrl(url);
        config.setDriverClassName("org.postgresql.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource(ParametersStoreProvider provider) {
        return createDataSource(
                provider.getParameter("db_username"),
                provider.getParameter("db_password"),
                provider.getParameter("db_url")
        );
    }
}
