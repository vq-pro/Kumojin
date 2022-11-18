package quebec.virtualite.kumojin.backend.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static quebec.virtualite.kumojin.utils.CollectionUtils.list;

@RunWith(MockitoJUnitRunner.class)
public class EventDomainTest
{
    private static final String NAME_A = "A";
    private static final String NAME_B = "B";

    @InjectMocks
    private EventDomain domain;

    @Mock
    private EventRepository mockedEventRepository;

    @Mock
    private EventModel mockedEventA;

    @Mock
    private EventModel mockedEventB;

    @Test
    public void addItem()
    {
        // When
        domain.addItem(NAME_A);

        // Then
        verify(mockedEventRepository).save(
            new EventModel()
                .setName(NAME_A));
    }

    @Test
    public void clear()
    {
        // When
        domain.clear();

        // Then
        verify(mockedEventRepository).deleteAll();
    }

    @Test
    public void exists_whenFalse()
    {
        // When
        boolean result = domain.exists(NAME_A);

        // Then
        verify(mockedEventRepository).findByName(NAME_A);

        assertThat(result).isFalse();
    }

    @Test
    public void exists_whenTrue()
    {
        // Given
        given(mockedEventRepository.findByName(NAME_A))
            .willReturn(mockedEventA);

        // When
        boolean result = domain.exists(NAME_A);

        // Then
        verify(mockedEventRepository).findByName(NAME_A);

        assertThat(result).isTrue();
    }

    @Test
    public void getEvents()
    {
        // Given
        given(mockedEventRepository.findAllByOrderByNameAsc())
            .willReturn(list(mockedEventA, mockedEventB));

        // When
        List<EventModel> results = domain.getEvents();

        // Then
        verify(mockedEventRepository).findAllByOrderByNameAsc();

        assertThat(results).isEqualTo(list(mockedEventA, mockedEventB));
    }

    @Test
    public void getEvents_whenEmpty_returnsEmptyList()
    {
        // Given
        given(mockedEventRepository.findAllByOrderByNameAsc())
            .willReturn(emptyList());

        // When
        List<EventModel> results = domain.getEvents();

        // Then
        assertThat(results).isEqualTo(emptyList());
    }

    @Test
    public void setEvents()
    {
        // Given
        List<EventModel> events = list(
            mockedEventB,
            mockedEventA);

        // When
        domain.setEvents(events);

        // Then
        verify(mockedEventRepository).saveAll(list(
            mockedEventB,
            mockedEventA));
    }
}
