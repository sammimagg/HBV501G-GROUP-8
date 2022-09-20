package is.hi.hbv501g.group8;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
    @Value("${spring.datasource.url}")
    private String dataSrc;
    @Value("${spring.datasource.username}")
    private String dataUsr;

    @Value("${spring.datasource.password}")
    private String dataPw;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataSrc);
        config.setUsername(dataUsr);
        config.setPassword(dataPw);
        return new HikariDataSource(config);
    }
}




/*
@SpringBootApplication
public class DatabaseConfig implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseConfig.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Test INSERT
        String insertSQL = "INSERT INTO Employees (SSN, lastName) " +
                "VALUES ('2911963149', 'Vilhjálmsson')";
        jdbcTemplate.update(insertSQL);

        // Test SELECT
        String selectSQL = "SELECT * FROM Employees";
        //ResultSet rs = jdbcTemplate.query(selectSQL, (rs));
    }
}
*/