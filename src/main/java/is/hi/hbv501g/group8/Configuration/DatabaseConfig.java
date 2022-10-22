/**
 * DatabaseConfig
 *
 * Description: Configuration file for database connection.
 *
 * @author kristófer Breki Gylfason - kbg15@hi.is
 * @author Halldór Jens Vilhjálsson - hjv6@hi.is
 * @author Samúel Magnússon - sam38@hi.is
 *
 * @/ TODO: 22.10.2022
 *      safely move to /Configuration/
 */

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


    /**
     * Description: Creates a HikariDataSource
     * @link application.p
     * @return new HikariDataSource
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataSrc);
        config.setUsername(dataUsr);
        config.setPassword(dataPw);
        return new HikariDataSource(config);
    }
}