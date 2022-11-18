package quebec.virtualite.kumojin.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import quebec.virtualite.kumojin.common.EventDefinition;
import quebec.virtualite.kumojin.web.pageobject.SampleAppPageObject;

import java.util.List;

@RequiredArgsConstructor
public class CucumberWebSteps
{
    private final SampleAppPageObject sampleAppPageObject;

    @When("^we add \"(.*)\"$")
    public void weAdd(String item)
    {
        sampleAppPageObject.add(item);
    }

    @When("we enter the application")
    public void weEnterApp()
    {
        sampleAppPageObject.startApplication();
    }

    @Then("we don't see an error message")
    public void weDontSeeErrorMessage()
    {
        sampleAppPageObject.validateErrorHidden();
    }

    @Then("^we see this error message: \"(.*)\"$")
    public void weSeeErrorMessage(String expectedMessage)
    {
        sampleAppPageObject
            .isViewing()
            .validateErrorMessage(expectedMessage);
    }

    @Then("we see this event list:")
    public void weSeeThisEventList(List<EventDefinition> expectedList)
    {
        sampleAppPageObject
            .isViewing()
            .validateList(expectedList);
    }
}
