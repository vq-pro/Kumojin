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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class RestServerTest
{
    private static final String NAME = "name";

    @InjectMocks
    private RestServer server;

    @Mock
    private EventDomain mockedDomain;

    @Mock
    private List<String> mockedItems;

    @Test
    public void addItem()
    {
        // Given
        AddItemRequest request = new AddItemRequest()
            .setName(NAME);

        // When
        ResponseEntity<Void> response = server.addItem(request);

        // Then
        verify(mockedDomain).exists(NAME);
        verify(mockedDomain).addItem(NAME);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @Test
    public void addItem_whenDuplicate_return409Status()
    {
        // Given
        AddItemRequest request = new AddItemRequest()
            .setName(NAME);

        given(mockedDomain.exists(NAME))
            .willReturn(true);

        // When
        ResponseEntity<Void> response = server.addItem(request);

        // Then
        verify(mockedDomain, never()).addItem(anyString());

        assertThat(response.getStatusCode()).isEqualTo(CONFLICT);
    }

    @Test
    public void addItem_whenErrorCodeGenerator_return418Status()
    {
        // Given
        AddItemRequest request = new AddItemRequest()
            .setName("error 418");

        // When
        ResponseEntity<Void> response = server.addItem(request);

        // Then
        verifyNoInteractions(mockedDomain);

        assertThat(response.getStatusCode()).isEqualTo(I_AM_A_TEAPOT);
    }

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
