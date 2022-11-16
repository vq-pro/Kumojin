package quebec.virtualite.kumojin.backend.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EventDomainTest
{
    private static final String NAME_A = "A";
    private static final String NAME_B = "B";

    @InjectMocks
    private EventDomain domain;

    @Mock
    private EventRepository mockedEventRepository;

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
    public void getItems()
    {
        // Given
        EventModel event1 = new EventModel().setName(NAME_A);
        EventModel event2 = new EventModel().setName(NAME_B);

        given(mockedEventRepository.findAll())
            .willReturn(asList(event1, event2));

        // When
        List<String> results = domain.getItems();

        // Then
        verify(mockedEventRepository).findAll();

        assertThat(results).isEqualTo(asList(NAME_A, NAME_B));
    }

    @Test
    public void getItems_whenEmpty_returnsEmptyList()
    {
        // Given
        given(mockedEventRepository.findAll())
            .willReturn(emptyList());

        // When
        List<String> results = domain.getItems();

        // Then
        assertThat(results).isEqualTo(emptyList());
    }

    @Test
    public void setItems()
    {
        // Given
        List<String> items = asList(NAME_B, NAME_A);

        // When
        domain.setItems(items);

        // Then
        verify(mockedEventRepository).saveAll(asList(
            new EventModel().setName(NAME_B),
            new EventModel().setName(NAME_A)));
    }
}
