package sg.com.officecleanings.workwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WorkWiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkWiseApplication.class, args);
    }

}
