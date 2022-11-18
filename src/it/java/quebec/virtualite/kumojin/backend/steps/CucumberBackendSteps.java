package quebec.virtualite.kumojin.backend.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import quebec.virtualite.kumojin.backend.rest.AddItemRequest;
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
     * Server Unit Test: {@link RestServerTest#addItem()}
     */
    @When("^we add the item \"(.*)\"$")
    public void weAddItem(String item)
    {
        rest.post("/items",
            new AddItemRequest()
                .setName(item));
    }

    /**
     * Server Unit Test: {@link RestServerTest#addItem()}
     */
    @When("^we add the item \"(.*)\" successfully$")
    public void weAddItemSuccessfully(String item)
    {
        weAddItem(item);
        assertThat(rest.response().statusCode()).isEqualTo(SC_CREATED);
    }

    /**
     * Server Unit Test: {@link RestServerTest#addItem()}
     */
    @When("we add the item with an empty name")
    public void weAddItemWithEmptyName()
    {
        rest.post("/items", new AddItemRequest());
    }

    /**
     * Server Unit Test: {@link RestServerTest#getEvents()}
     */
    @When("we ask for the event list")
    public void weAskForTheEventList()
    {
        rest.get("/events");
    }

    @When("^we get a (.*) error$")
    public void weGetError(int expectedStatusCode)
    {
        assertThat(rest.response().statusCode()).isEqualTo(expectedStatusCode);
    }

    @Then("we get an empty list")
    public void weGetEmptyList()
    {
        assertThat(rest.response().statusCode()).isEqualTo(SC_OK);

        GetListResponse response = rest.response().as(GetListResponse.class);
        assertThat(response.getEvents()).isEqualTo(emptyList());
    }

    @Then("we get this:")
    public void weGetThis(List<EventDefinition> expectedEvents)
    {
        assertThat(rest.response().statusCode()).isEqualTo(SC_OK);

        GetListResponse response = rest.response().as(GetListResponse.class);
        assertThat(transform(response.getEvents(), row ->
            new EventDefinition()
                .setName(row.getName())
                .setDescription(row.getDescription()))).isEqualTo(expectedEvents);
    }
}
