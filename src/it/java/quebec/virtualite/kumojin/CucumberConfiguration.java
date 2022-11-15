package quebec.virtualite.kumojin;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class CucumberConfiguration
{
}
