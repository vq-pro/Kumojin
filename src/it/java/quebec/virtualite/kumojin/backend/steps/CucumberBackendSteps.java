package quebec.virtualite.kumojin.backend.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import quebec.virtualite.kumojin.backend.rest.AddEventRequest;
import quebec.virtualite.kumojin.backend.rest.GetListResponse;
import quebec.virtualite.kumojin.backend.rest.RestServerTest;
import quebec.virtualite.kumojin.backend.utils.RestClient;
import quebec.virtualite.kumojin.common.EventDefinition;

import java.util.List;

import static java.util.Collections.emptyList;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static quebec.virtualite.kumojin.utils.CollectionUtils.transform;

public class CucumberBackendSteps
{
    private final RestClient rest;

    public CucumberBackendSteps(@Value("${local.server.port}") int serverPort)
    {
        this.rest = new RestClient(serverPort);
    }

    /**
     * Server Unit Test: {@link RestServerTest#addEvent()}
     */
    @When("we POST this event:")
    public void postEvent(EventDefinition event)
    {
        rest.post("/events",
            new AddEventRequest()
                .setName(event.getName())
                .setDescription(event.getDescription()));
    }

    /**
     * Server Unit Test: {@link RestServerTest#addEvent()}
     */
    @When("we POST this event successfully:")
    public void postEventSuccessfully(EventDefinition event)
    {
        postEvent(event);
        assertThat(rest.response().statusCode()).isEqualTo(SC_CREATED);
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
        assertThat(rest.response().statusCode()).isEqualTo(expectedStatusCode);
    }

    @Then("we receive an empty list")
    public void receiveEmptyList()
    {
        assertThat(rest.response().statusCode()).isEqualTo(SC_OK);

        GetListResponse response = rest.response().as(GetListResponse.class);
        assertThat(response.getEvents()).isEqualTo(emptyList());
    }

    @Then("we receive this:")
    public void receiveThis(List<EventDefinition> expectedEvents)
    {
        assertThat(rest.response().statusCode()).isEqualTo(SC_OK);

        GetListResponse response = rest.response().as(GetListResponse.class);
        assertThat(transform(response.getEvents(), row ->
            new EventDefinition()
                .setName(row.getName())
                .setDescription(row.getDescription()))).isEqualTo(expectedEvents);
    }
}
