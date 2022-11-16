package quebec.virtualite.kumojin.backend.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import quebec.virtualite.kumojin.backend.rest.AddItemRequest;
import quebec.virtualite.kumojin.backend.rest.GetListResponse;
import quebec.virtualite.kumojin.backend.rest.RestServerTest;
import quebec.virtualite.kumojin.backend.utils.RestClient;

import java.util.List;

import static java.util.Collections.emptyList;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

public class CucumberBackendSteps
{
    private final RestClient rest;

    public CucumberBackendSteps(
        @Value("${local.server.port}") int serverPort)
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

        assertThat(rest.response().statusCode()).isEqualTo(SC_OK);
    }

    /**
     * Server Unit Test: {@link RestServerTest#getItems()}
     */
    @When("we ask for the list")
    public void weAskForTheList()
    {
        rest.get("/items");
    }

    @Then("we get an empty list")
    public void weGetEmptyList()
    {
        assertThat(rest.response().statusCode()).isEqualTo(SC_OK);

        GetListResponse response = rest.response().as(GetListResponse.class);
        assertThat(response.getItems()).isEqualTo(emptyList());
    }

    @Then("we get this:")
    public void weGetThis(List<String> expectedItems)
    {
        assertThat(rest.response().statusCode()).isEqualTo(SC_OK);

        GetListResponse response = rest.response().as(GetListResponse.class);
        assertThat(response.getItems()).isEqualTo(expectedItems);
    }
}
