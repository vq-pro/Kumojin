package quebec.virtualite.kumojin.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import quebec.virtualite.kumojin.Application;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class CucumberConfiguration
{
}
