package quebec.virtualite.kumojin.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import quebec.virtualite.kumojin.Application;
import quebec.virtualite.kumojin.web.pageobject.SampleAppPageObject;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT, classes = Application.class)
public class CucumberWebSteps
{
    private final SampleAppPageObject sampleAppPageObject;

    CucumberWebSteps(SampleAppPageObject sampleAppPageObject)
    {
        this.sampleAppPageObject = sampleAppPageObject;
    }

    @When("we enter the application")
    public void weEnterApp()
    {
        //        fail("enter app");
        sampleAppPageObject.startApplication();
    }

    @Then("we see this list:")
    public void weSeeThisList(List<String> expectedList)
    {
        sampleAppPageObject
            .isViewing()
            .validateList(expectedList);
    }
}
