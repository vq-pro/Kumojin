package quebec.virtualite.kumojin.web.pageobject;

import io.cucumber.java.AfterAll;
import quebec.virtualite.kumojin.web.utils.PageObject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleAppPageObject extends PageObject
{
    private static final String ID_ADD_BUTTON = "add";
    private static final String ID_ERROR_MESSAGE = "error";
    private static final String ID_ITEM = "item";
    private static final String ID_NAME = "name";
    private static final String ID_TITLE = "title";

    private static final String URL = "http://localhost:8080/index.html";

    @AfterAll
    public static void afterAllScenarios()
    {
        stopWebBrowser();
    }

    public void add(String item)
    {
        browser.set(ID_NAME, item);
        browser.click(ID_ADD_BUTTON);
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

    public SampleAppPageObject validateErrorHidden()
    {
        poll(() -> validateHidden(ID_ERROR_MESSAGE));

        return this;
    }

    public void validateErrorMessage(String expectedMessage)
    {
        validateDisplayed(ID_ERROR_MESSAGE, expectedMessage);
    }

    public void validateList(List<String> expectedList)
    {
        poll(() -> assertThat(browser.elementsText(ID_ITEM)).isEqualTo(expectedList));
    }
}
