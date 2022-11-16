package quebec.virtualite.kumojin.web.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertTrue;

public abstract class PageObject
{
    protected static WebBrowser browser;

    protected static void startWebBrowser()
    {
        if (browser == null)
        {
            browser = new WebBrowser();
        }
    }

    protected static void stopWebBrowser()
    {
        if (browser != null)
        {
            browser.close();
            browser = null;
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
            browser.elementAttribute(idElement, "disabled"), not("disabled"));
    }

    protected void validateElementExists(String idElement)
    {
        assertTrue("Element " + idElement + " doesn't exist", browser.exists(idElement));
    }

    protected void validateElementText(String idElement, String expectedText)
    {
        assertThat("Bad text for '" + idElement + "'",
            browser.elementText(idElement), is(expectedText));
    }
}
