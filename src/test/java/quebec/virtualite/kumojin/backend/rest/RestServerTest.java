package quebec.virtualite.kumojin.backend.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class RestServerTest
{
    @InjectMocks
    private RestServer server;

    @Test
    public void getItems()
    {
        // When
        ResponseEntity<GetListResponse> response = server.getItems();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(
            new GetListResponse()
                .setItems(asList("Apple", "Banana")));
    }
}
