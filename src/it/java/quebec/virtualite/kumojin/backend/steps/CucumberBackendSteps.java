package quebec.virtualite.kumojin.backend.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import quebec.virtualite.kumojin.backend.rest.AddEventRequest;
import quebec.virtualite.kumojin.backend.rest.GetListResponse;
import quebec.virtualite.kumojin.backend.rest.RestServerTest;
import quebec.virtualite.kumojin.backend.utils.RestClient;
import quebec.virtualite.kumojin.common.EventTableRow;

import java.util.List;

import static java.util.Collections.emptyList;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static quebec.virtualite.kumojin.common.steps.CucumberCommonSteps.tableFrom;
import static quebec.virtualite.kumojin.utils.CollectionUtils.list;

public class CucumberBackendSteps
{
    private static final List<String> EVENT_LIST_HEADER = list("name", "description", "start", "end");

    private final RestClient rest;

    public CucumberBackendSteps(@Value("${local.server.port}") int serverPort)
    {
        this.rest = new RestClient(serverPort);
    }

    /**
     * Server Unit Test: {@link RestServerTest#addEvent()}
     */
    @When("we POST this event:")
    public void postEvent(EventTableRow event)
    {
        rest.post("/events",
            new AddEventRequest()
                .setName(event.getName())
                .setDescription(event.getDescription())
                .setTimezone(event.getTimezone())
                .setStart(event.getStart())
                .setEnd(event.getEnd()));
    }

    /**
     * Server Unit Test: {@link RestServerTest#addEvent()}
     */
    @When("we POST this event successfully:")
    public void postEventSuccessfully(EventTableRow event)
    {
        postEvent(event);
        assertThat(rest.response().statusCode(), equalTo(SC_CREATED));
    }

    /**
     * Server Unit Test: {@link RestServerTest#getEvents()}
     */
    @When("we GET the event list")
    public void getEventList()
    {
        rest.get("/events");
    }

    @When("^we receive a (.*) error$")
    public void receiveError(int expectedStatusCode)
    {
        assertThat(rest.response().statusCode(), equalTo(expectedStatusCode));
    }

    @Then("we receive an empty list")
    public void receiveEmptyList()
    {
        assertThat(rest.response().statusCode(), equalTo(SC_OK));

        GetListResponse response = rest.response().as(GetListResponse.class);
        assertThat(response.getEvents(), equalTo(emptyList()));
    }

    @Then("we receive this:")
    public void receiveThis(DataTable expectedEvents)
    {
        assertThat(rest.response().statusCode(), equalTo(SC_OK));

        GetListResponse response = rest.response().as(GetListResponse.class);
        expectedEvents.diff(tableFrom(
            EVENT_LIST_HEADER, response.getEvents(),
            row -> list(row.getName(), row.getDescription(), row.getStart(), row.getEnd())));
    }
}
