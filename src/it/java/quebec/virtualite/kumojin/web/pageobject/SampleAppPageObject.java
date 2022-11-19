package quebec.virtualite.kumojin.web.pageobject;

import io.cucumber.java.AfterAll;
import quebec.virtualite.kumojin.common.EventTableRow;
import quebec.virtualite.kumojin.web.utils.PageObject;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static quebec.virtualite.kumojin.utils.CollectionUtils.list;
import static quebec.virtualite.kumojin.utils.CollectionUtils.transform;

public class SampleAppPageObject extends PageObject
{
    private static final String ID_ADD_BUTTON = "add";
    private static final String ID_DESCS = "descs";
    private static final String ID_DESCRIPTION = "desc";
    private static final String ID_ERROR_MESSAGE = "error";
    private static final String ID_NAME = "name";
    private static final String ID_NAMES = "names";
    private static final String ID_STARTS = "starts";
    private static final String ID_TABLE_HEADER = "header";
    private static final String ID_TITLE = "title";

    private static final List<String> TABLE_HEADER = list("Name", "Description", "Start");

    private static final String URL = "http://localhost:8080/index.html";

    @AfterAll
    public static void afterAllScenarios()
    {
        stopWebBrowser();
    }

    public SampleAppPageObject add(EventTableRow event)
    {
        browser
            .set(ID_NAME, event.getName())
            .set(ID_DESCRIPTION, event.getDescription())
            .click(ID_ADD_BUTTON);
        return this;
    }

    public void addFormIsCleared()
    {
        validateElementText(ID_NAME, "");
        validateElementText(ID_DESCRIPTION, "");
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

    public void validateList(List<EventTableRow> expectedList)
    {
        poll(() -> {
            assertThat("Invalid table header",
                browser.elementsText(ID_TABLE_HEADER), equalTo(TABLE_HEADER));

            assertThat(browser.elementsText(ID_NAMES), equalTo(
                transform(expectedList, EventTableRow::getName)));

            assertThat(browser.elementsText(ID_DESCS), equalTo(
                transform(expectedList, EventTableRow::getDescription)));

            assertThat(browser.elementsText(ID_STARTS), equalTo(
                transform(expectedList, EventTableRow::getStart)));
        });
    }
}
