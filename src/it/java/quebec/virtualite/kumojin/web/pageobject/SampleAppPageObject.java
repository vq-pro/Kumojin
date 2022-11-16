package quebec.virtualite.kumojin.web.pageobject;

import io.cucumber.java.AfterAll;
import quebec.virtualite.kumojin.web.utils.PageObject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleAppPageObject extends PageObject
{
    protected static final String ID_ITEM = "item";
    protected static final String ID_TITLE = "title";
    protected static final String URL = "http://localhost:8080/index.html";

    @AfterAll
    public static void afterAllScenarios()
    {
        stopWeb();
    }

    public SampleAppPageObject isViewing()
    {
        validateIsDisplayed(ID_TITLE, "Kumojin");
        return this;
    }

    public void startApplication()
    {
        startWeb();
        web.go(URL);
    }

    public void validateList(List<String> expectedList)
    {
        assertThat(web.elementsText(ID_ITEM))
            .isEqualTo(expectedList);
    }
}
