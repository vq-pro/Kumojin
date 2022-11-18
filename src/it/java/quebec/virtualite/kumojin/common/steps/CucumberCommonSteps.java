package quebec.virtualite.kumojin.common.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import quebec.virtualite.kumojin.backend.domain.EventDomain;
import quebec.virtualite.kumojin.backend.domain.EventDomainTest;
import quebec.virtualite.kumojin.backend.domain.EventModel;
import quebec.virtualite.kumojin.common.EventDefinition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static quebec.virtualite.kumojin.utils.CollectionUtils.list;
import static quebec.virtualite.kumojin.utils.CollectionUtils.transform;

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
     * Unit Test: {@link EventDomainTest#setEvents()}
     */
    @Given("these predefined events:")
    public void thesePredefinedEvents(List<EventDefinition> events)
    {
        domain.setEvents(
            transform(events,
                def -> new EventModel()
                    .setName(def.getName())
                    .setDescription(def.getDescription())));
    }

    @DataTableType
    public List<EventDefinition> readEventsFromTable(DataTable table)
    {
        assertThat(table.row(0)).isEqualTo(list("name", "description"));

        return transform(table.entries(),
            row -> new EventDefinition()
                .setName(row.get("name"))
                .setDescription(row.get("description")));
    }
}
