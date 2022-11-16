package quebec.virtualite.kumojin.common.steps;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import quebec.virtualite.kumojin.backend.domain.EventDomain;
import quebec.virtualite.kumojin.backend.domain.EventDomainTest;

import java.util.List;

@RequiredArgsConstructor
public class CucumberCommonSteps
{
    private final EventDomain domain;

    @Before
    public void beforeEachScenario()
    {
        domain.clear();
    }

    /**
     * Unit Test: {@link EventDomainTest#setItems()}
     */
    @Given("these predefined items:")
    public void thesePredefinedItems(List<String> items)
    {
        domain.setItems(items);
    }
}
