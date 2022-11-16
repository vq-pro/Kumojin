package quebec.virtualite.kumojin.web.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertTrue;

public abstract class PageObject
{
    protected static WebClientService web;

    public String dumpSourceToStdout()
    {
        String html = web.getHtml();
        System.out.println(html);

        return html;
    }

    protected static void startWeb()
    {
        if (web == null)
        {
            web = new WebClientService();
        }
    }

    protected static void stopWeb()
    {
        if (web != null)
        {
            web.close();
            web = null;
        }
    }

    protected void validateIsDisplayed(String idElement, String expectedText)
    {
        validateElementExists(idElement);
        validateElementEnabled(idElement);
        validateElementText(idElement, expectedText);
    }

    protected void validateElementEnabled(String idElement)
    {
        assertThat("'" + idElement + "' should not be disabled",
            web.elementAttribute(idElement, "disabled"), not("disabled"));
    }

    protected void validateElementExists(String idElement)
    {
        assertTrue("Element " + idElement + " doesn't exist", web.exists(idElement));
    }

    protected void validateElementText(String idElement, String expectedText)
    {
        assertThat("Bad text for '" + idElement + "'",
            web.elementText(idElement), is(expectedText));
    }
}
