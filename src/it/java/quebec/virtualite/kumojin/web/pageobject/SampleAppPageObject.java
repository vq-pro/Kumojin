package quebec.virtualite.kumojin.web.pageobject;

import io.cucumber.java.AfterAll;
import quebec.virtualite.kumojin.common.EventDefinition;
import quebec.virtualite.kumojin.web.utils.PageObject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static quebec.virtualite.kumojin.utils.CollectionUtils.transform;

public class SampleAppPageObject extends PageObject
{
    private static final String ID_ADD_BUTTON = "add";
    private static final String ID_DESCS = "descs";
    private static final String ID_DESCRIPTION = "desc";
    private static final String ID_ERROR_MESSAGE = "error";
    private static final String ID_NAME = "name";
    private static final String ID_NAMES = "names";
    private static final String ID_TITLE = "title";

    private static final String URL = "http://localhost:8080/index.html";

    @AfterAll
    public static void afterAllScenarios()
    {
        stopWebBrowser();
    }

    public void add(EventDefinition event)
    {
        browser
            .set(ID_NAME, event.getName())
            .set(ID_DESCRIPTION, event.getDescription())
            .click(ID_ADD_BUTTON);
    }

    public SampleAppPageObject isViewing()
    {
        validateDisplayed(ID_TITLE, "Kumojin");
        return this;
    }

    public void startApplication()
    {
        startWebBrowser();
        browser.go(URL);
    }

    public void validateErrorHidden()
    {
        poll(() -> validateHidden(ID_ERROR_MESSAGE));
    }

    public void validateErrorMessage(String expectedMessage)
    {
        validateDisplayed(ID_ERROR_MESSAGE, expectedMessage);
    }

    public void validateList(List<EventDefinition> expectedList)
    {
        poll(() -> {
            assertThat(browser.elementsText(ID_NAMES)).isEqualTo(
                transform(expectedList, EventDefinition::getName));

            assertThat(browser.elementsText(ID_DESCS)).isEqualTo(
                transform(expectedList, EventDefinition::getDescription));
        });
    }
}
