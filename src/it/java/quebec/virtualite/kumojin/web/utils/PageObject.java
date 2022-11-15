package quebec.virtualite.kumojin.web.utils;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RequiredArgsConstructor
public abstract class PageObject
{
    protected final WebClientService web;

    public String dumpSourceToStdout()
    {
        String html = web.driver.getPageSource();
        System.out.println(html);

        return html;
    }

    protected WebElement element(String id)
    {
        WebElement element = web.driver.findElement(By.id(id));
        assertNotNull("element '" + id + "' not found", element);

        return element;
    }

    protected String elementText(String id)
    {
        return element(id).getAttribute("innerText").trim();
    }

    protected List<WebElement> elements(String id)
    {
        List<WebElement> elements = web.driver.findElements(By.id(id));
        assertThat("elements '" + id + "' not found", elements, not(empty()));

        return elements;
    }

    protected List<String> elementsText(String id)
    {
        return exists(id)
               ? elements(id).stream()
                   .map(element -> element.getAttribute("innerText"))
                   .map(String::trim)
                   .collect(toList())
               : emptyList();
    }

    protected boolean exists(String id)
    {
        return web.driver.findElements(By.id(id)) != null;
    }

    protected void go(String url)
    {
        try
        {
            web.driver.get(url);

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    protected void validateIsDisplayed(String idElement, String expectedText)
    {
        validateElementExists(idElement);
        validateElementEnabled(idElement);
        validateElementText(idElement, expectedText);
    }

    private String elementAttribute(String idElement, String attribute)
    {
        return element(idElement).getAttribute(attribute);
    }

    private void validateElementEnabled(String idElement)
    {
        assertThat("'" + idElement + "' should not be disabled",
            elementAttribute(idElement, "disabled"), not("disabled"));
    }

    private void validateElementExists(String idElement)
    {
        assertTrue("Element " + idElement + " doesn't exist", exists(idElement));
    }

    private void validateElementText(String idElement, String expectedText)
    {
        assertThat("Bad text for '" + idElement + "'",
            elementText(idElement), is(expectedText));
    }
}
