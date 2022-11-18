package quebec.virtualite.kumojin.backend.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import quebec.virtualite.kumojin.backend.domain.EventDomain;
import quebec.virtualite.kumojin.backend.domain.EventModel;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.http.HttpStatus.OK;
import static quebec.virtualite.kumojin.utils.CollectionUtils.list;

@RunWith(MockitoJUnitRunner.class)
public class RestServerTest
{
    private static final String DESC = "desc";
    private static final String DESC_A = "descA";
    private static final String DESC_B = "descB";
    private static final String NAME = "name";
    private static final String NAME_A = "nameA";
    private static final String NAME_B = "nameB";

    @InjectMocks
    private RestServer server;

    @Mock
    private EventDomain mockedDomain;

    @Before
    public void init()
    {
        server.isTesting = true;
    }

    @Test
    public void addEvent()
    {
        // Given
        AddEventRequest request = new AddEventRequest()
            .setName(NAME)
            .setDescription(DESC);

        // When
        ResponseEntity<Void> response = server.addEvent(request);

        // Then
        verify(mockedDomain).exists(NAME);
        verify(mockedDomain).addEvent(new EventModel()
            .setName(NAME)
            .setDescription(DESC));

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
    }

    @Test
    public void addEvent_whenDuplicate_return409Status()
    {
        // Given
        AddEventRequest request = new AddEventRequest()
            .setName(NAME)
            .setDescription(DESC);

        given(mockedDomain.exists(NAME))
            .willReturn(true);

        // When
        ResponseEntity<Void> response = server.addEvent(request);

        // Then
        verify(mockedDomain, never()).addEvent(any());

        assertThat(response.getStatusCode()).isEqualTo(CONFLICT);
    }

    @Test
    public void addEvent_whenErrorCodeGenerator_return418Status()
    {
        // Given
        AddEventRequest request = new AddEventRequest()
            .setName("error 418");

        // When
        ResponseEntity<Void> response = server.addEvent(request);

        // Then
        verifyNoInteractions(mockedDomain);

        assertThat(response.getStatusCode()).isEqualTo(I_AM_A_TEAPOT);
    }

    @Test
    public void getEvents()
    {
        // Given
        EventModel modelA = new EventModel()
            .setName(NAME_A)
            .setDescription(DESC_A);
        EventModel modelB = new EventModel()
            .setName(NAME_B)
            .setDescription(DESC_B);

        given(mockedDomain.getEvents())
            .willReturn(list(modelA, modelB));

        // When
        ResponseEntity<GetListResponse> response = server.getEvents();

        // Then
        verify(mockedDomain).getEvents();

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(
            new GetListResponse().setEvents(list(
                new GetListResponse.Row()
                    .setName(NAME_A)
                    .setDescription(DESC_A),
                new GetListResponse.Row()
                    .setName(NAME_B)
                    .setDescription(DESC_B))));
    }

    @Test
    public void getEvents_whenEmpty_returnsEmptyList()
    {
        // Given
        given(mockedDomain.getEvents())
            .willReturn(emptyList());

        // When
        ResponseEntity<GetListResponse> response = server.getEvents();

        // Then
        verify(mockedDomain).getEvents();

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(
            new GetListResponse()
                .setEvents(emptyList()));
    }
}
