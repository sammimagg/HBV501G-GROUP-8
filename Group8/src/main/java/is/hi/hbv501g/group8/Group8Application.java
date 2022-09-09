package is.hi.hbv501g.group8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Group8Application {

    public static void main(String[] args) {
        SpringApplication.run(Group8Application.class, args);


        System.out.println("Er DeadTime ekki hellað nafn?");
    }

}
