package quebec.virtualite.kumojin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"quebec.virtualite.*"})
public class Application
{
    public static void main(final String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
