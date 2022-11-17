package quebec.virtualite.kumojin.web.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unused")
public abstract class PageObject
{
    private static final long POLL_INTERVAL = 250;
    private static final long POLL_TIMEOUT = 3000;

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
        Throwable exception;
        long elapsed;
        long start = System.currentTimeMillis();

        do
        {
            sleep(POLL_INTERVAL);

            try
            {
                callback.run();
                return;
            }
            catch (Throwable t)
            {
                exception = t;
            }

            elapsed = System.currentTimeMillis() - start;
        }
        while (elapsed < POLL_TIMEOUT);

        throw new RuntimeException(exception);
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

    private void sleep(long delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
