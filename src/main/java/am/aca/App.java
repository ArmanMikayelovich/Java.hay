package am.aca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App implements CommandLineRunner
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }

    @Override
    public void run(String[] args) {
        for (int x = 0; x < 10; ++x) {
            System.out.println();
        }


    }


}
