package quebec.virtualite.kumojin.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import quebec.virtualite.kumojin.Application;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class CucumberWebSteps
{
    @When("we enter the application")
    public void weEnterApp()
    {
        //        fail("enter app");
    }

    @Then("we see this list:")
    public void weSeeThisList(List<String> expectedList)
    {
    }
}
