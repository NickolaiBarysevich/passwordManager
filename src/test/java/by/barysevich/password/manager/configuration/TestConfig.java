package by.barysevich.password.manager.configuration;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ComponentScan("by.barysevich.password.manager.repository")
@PropertySource("classpath:application.yml")
public class TestConfig {

    private EmbeddedPostgres postgres;

    /**
     * Constructs the config with embedded postgres db.
     *
     * @throws IOException if any error during db creations occurs
     */
    public TestConfig() throws IOException {
        postgres = EmbeddedPostgres.builder()
                .setPort(3333)
                .start();
    }

    /**
     * Returns test datasource.
     *
     * @return test datasource
     */
    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();
        config.setDataSource(postgres.getPostgresDatabase());
        return new HikariDataSource(config);
    }

    /**
     * Declaration of Flyway bean witch execute SQL scripts on DB.
     *
     * @param dataSource of an embedded database
     * @return Flyway bean
     */
    @Bean
    public Flyway flyway(DataSource dataSource) {
        var config = new ClassicConfiguration();
        config.setDataSource(dataSource);
        config.setLocationsAsStrings("classpath:db/migration/test");
        var flyway = new Flyway(config);
        flyway.migrate();
        return flyway;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @PreDestroy
    public void closeDb() throws IOException {
        postgres.close();
    }
}
