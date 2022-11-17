package quebec.virtualite.kumojin.web.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
public abstract class PageObject
{
    private static final long TIMEOUT = 5000;

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

    protected void poll(Runnable callback)
    {
        RuntimeException exception;
        long now;
        long start = System.currentTimeMillis();

        do
        {
            try
            {
                callback.run();
                return;
            }
            catch (RuntimeException t)
            {
                exception = t;
            }

            now = System.currentTimeMillis();
        }
        while (now - start < TIMEOUT);

        throw exception;
    }

    protected void validateDisabled(String idElement)
    {
        assertThat("'" + idElement + "' should be disabled",
            browser.elementAttribute(idElement, "disabled"), is("disabled"));
    }

    protected void validateDisplayed(String idElement, String expectedText)
    {
        validateExists(idElement);
        validateEnabled(idElement);
        validateElementText(idElement, expectedText);
    }

    protected void validateElementText(String idElement, String expectedText)
    {
        assertThat("Bad text for '" + idElement + "'",
            browser.elementText(idElement), is(expectedText));
    }

    protected void validateEnabled(String idElement)
    {
        assertThat("'" + idElement + "' should not be disabled",
            browser.elementAttribute(idElement, "disabled"), not("disabled"));
        assertThat("'" + idElement + "' should not be hidden",
            browser.elementAttribute(idElement, "hidden"), is(nullValue()));
    }

    protected void validateExists(String idElement)
    {
        assertTrue("Element " + idElement + " doesn't exist", browser.exists(idElement));
    }

    protected void validateHidden(String idElement)
    {
        validateExists(idElement);
        assertThat("'" + idElement + "' should be hidden",
            browser.elementAttribute(idElement, "hidden"), is("true"));
    }
}
