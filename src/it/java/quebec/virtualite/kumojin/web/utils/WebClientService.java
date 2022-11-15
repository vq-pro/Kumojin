package quebec.virtualite.kumojin.web.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static java.time.Duration.ofMillis;

@Service
public class WebClientService
{
    WebDriver driver;

    @PreDestroy
    void close()
    {
        driver.quit();
    }

    @PostConstruct
    void init()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(ofMillis(500));
        driver.manage().window().maximize();
    }
}
