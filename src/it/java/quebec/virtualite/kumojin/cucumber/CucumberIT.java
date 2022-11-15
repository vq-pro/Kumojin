package quebec.virtualite.kumojin.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions
    (
        features = "src/features",
        plugin = {
            "summary",
            "html:target/cucumber-reports.html"
        },
        snippets = CAMELCASE,
        tags = "not @Ignore"
    )
public class CucumberIT
{
}
