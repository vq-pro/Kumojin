package quebec.virtualite.kumojin.web.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static java.time.Duration.ofMillis;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertNotNull;

public class WebBrowser
{
    private final WebDriver driver;

    public WebBrowser()
    {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(ofMillis(500));
        driver.manage().window().maximize();
    }

    public void close()
    {
        driver.quit();
    }

    public String getHtml()
    {
        return driver.getPageSource();
    }

    public WebElement element(String id)
    {
        WebElement element = driver.findElement(By.id(id));
        assertNotNull("element '" + id + "' not found", element);

        return element;
    }

    public String elementAttribute(String idElement, String attribute)
    {
        return element(idElement).getAttribute(attribute);
    }

    public String elementText(String id)
    {
        return element(id).getAttribute("innerText").trim();
    }

    public List<WebElement> elements(String id)
    {
        List<WebElement> elements = driver.findElements(By.id(id));
        assertThat("elements '" + id + "' not found", elements, not(empty()));

        return elements;
    }

    public List<String> elementsText(String id)
    {
        return exists(id)
               ? elements(id).stream()
                   .map(element -> element.getAttribute("innerText"))
                   .map(String::trim)
                   .collect(toList())
               : emptyList();
    }

    public boolean exists(String id)
    {
        return driver.findElements(By.id(id)) != null;
    }

    public void go(String url)
    {
        try
        {
            driver.get(url);

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
