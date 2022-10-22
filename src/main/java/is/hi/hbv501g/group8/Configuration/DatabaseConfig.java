/**
 * DatabaseConfig
 *
 * Description: Configuration file for database connection.
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 */

package is.hi.hbv501g.group8.Configuration;

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

    /**
     * Description: Generates a JdbcTemplate
     *
     * @param dataSource DataSource
     * @return JdbcTemplate new jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    /**
     * Description: Creates a HikariDataSource
     *
     * @return new HikariDataSource
     * @throws Exception improperly defined applications.properties file in resources.
     */
    @Bean
    public DataSource dataSource() throws Exception {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dataSrc);
            config.setUsername(dataUsr);
            config.setPassword(dataPw);
            return new HikariDataSource(config);
        } catch (Exception e) {
            System.out.println("application.properties improper configuration");
            return null;
        }
    }
}