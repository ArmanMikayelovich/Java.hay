package am.aca;

import am.aca.entity.*;
import am.aca.repository.*;
import am.aca.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hello world!
 */
@SpringBootApplication
public class App  {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }


}
