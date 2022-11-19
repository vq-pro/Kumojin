package quebec.virtualite.kumojin.common.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import lombok.RequiredArgsConstructor;
import quebec.virtualite.kumojin.backend.domain.EventDomain;
import quebec.virtualite.kumojin.backend.domain.EventDomainTest;
import quebec.virtualite.kumojin.backend.domain.EventModel;
import quebec.virtualite.kumojin.common.EventTableRow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertTrue;
import static quebec.virtualite.kumojin.utils.CollectionUtils.list;
import static quebec.virtualite.kumojin.utils.CollectionUtils.transform;
import static quebec.virtualite.kumojin.utils.DateTimeUtils.parseTimestamp;

@RequiredArgsConstructor
public class CucumberCommonSteps
{
    private static final List<String> HEADER = list("name", "description", "start");
    private static final List<String> HEADER_WITH_TIMEZONE = list("name", "description", "timezone", "start");

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
    public void thesePredefinedEvents(List<EventTableRow> events)
    {
        domain.setEvents(
            transform(events,
                def -> new EventModel()
                    .setName(def.getName())
                    .setDescription(def.getDescription())
                    .setTimezone(def.getTimezone())
                    .setStart(parseTimestamp(def.getStart(), def.getTimezone()))));
    }

    @DataTableType
    public List<EventTableRow> readEventsFromTable(DataTable table)
    {
        assertTrue(
            HEADER.equals(table.row(0)) ||
            HEADER_WITH_TIMEZONE.equals(table.row(0)));

        return transform(table.entries(),
            row -> new EventTableRow()
                .setName(row.get("name"))
                .setDescription(row.get("description"))
                .setTimezone(row.get("timezone"))
                .setStart(row.get("start")));
    }

    public static <T> DataTable tableFrom(List<String> headerRow, List<T> rows,
        Function<T, List<String>> callback)
    {
        List<List<String>> rawTable = new ArrayList<>();
        rawTable.add(headerRow);

        for (T row : rows)
        {
            rawTable.add(callback.apply(row));
        }

        return DataTable.create(rawTable);
    }
}
