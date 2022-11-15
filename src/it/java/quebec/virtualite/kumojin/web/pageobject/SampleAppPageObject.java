package quebec.virtualite.kumojin.web.pageobject;

import org.springframework.stereotype.Component;
import quebec.virtualite.kumojin.web.utils.PageObject;
import quebec.virtualite.kumojin.web.utils.WebClientService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Component
public class SampleAppPageObject extends PageObject
{
    protected static final String ID_ITEM = "item";
    protected static final String ID_TITLE = "title";
    protected static final String URL = "http://localhost:8080/index.html";

    public SampleAppPageObject(WebClientService web)
    {
        super(web);
    }

    public SampleAppPageObject isViewing()
    {
        validateIsDisplayed(ID_TITLE, "Kumojin");
        return this;
    }

    public void startApplication()
    {
        go(URL);
    }

    public void validateList(List<String> expectedList)
    {
        assertThat(elementsText(ID_ITEM))
            .isEqualTo(expectedList);
    }
}
