package quebec.virtualite.kumojin.backend.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import quebec.virtualite.kumojin.backend.domain.EventDomain;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class RestServerTest
{
    @InjectMocks
    private RestServer server;

    @Mock
    private EventDomain mockedDomain;

    @Mock
    private List<String> mockedItems;

    @Test
    public void getItems()
    {
        // Given
        given(mockedDomain.getItems())
            .willReturn(mockedItems);

        // When
        ResponseEntity<GetListResponse> response = server.getItems();

        // Then
        verify(mockedDomain).getItems();

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(
            new GetListResponse()
                .setItems(mockedItems));
    }

    @Test
    public void getItems_whenEmpty_returnsEmptyList()
    {
        // Given
        given(mockedDomain.getItems())
            .willReturn(emptyList());

        // When
        ResponseEntity<GetListResponse> response = server.getItems();

        // Then
        verify(mockedDomain).getItems();

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(
            new GetListResponse()
                .setItems(emptyList()));
    }
}
