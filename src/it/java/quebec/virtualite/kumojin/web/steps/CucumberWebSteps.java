package quebec.virtualite.kumojin.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import quebec.virtualite.kumojin.web.pageobject.SampleAppPageObject;

import java.util.List;

@RequiredArgsConstructor
public class CucumberWebSteps
{
    private final SampleAppPageObject sampleAppPageObject;

    @When("we enter the application")
    public void weEnterApp()
    {
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
