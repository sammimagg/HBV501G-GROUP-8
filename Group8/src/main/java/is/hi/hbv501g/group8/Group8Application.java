package is.hi.hbv501g.group8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
public class Group8Application implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Group8Application.class, args);
        System.out.println("Er DeadTime ekki hellað nafn?");
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value="name", defaultValue = "ekkert-input") String name) {
        System.out.println("Keyrist!");
        return String.format(("Hello %s!"), name);
    }

    @Override
    public void run(String... args) throws Exception {
        // prufa insert
        String testInsertSQL = "INSERT INTO Employees (SSN, lastName, company, jobtitle, phonenumber) " +
                "VALUES ('2911963149', 'Vilhjálmsson', 'Háskóli Íslands', 'Student', '7918151')";
        jdbcTemplate.update(testInsertSQL);

        // Prufa select

    }
}
