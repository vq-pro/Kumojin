package quebec.virtualite.kumojin.backend.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EventDomainTest
{
    @InjectMocks
    private EventDomain domain;

    @Test
    public void setItems()
    {
        // Given
        List<String> items = asList("B", "A");

        // When
        domain.setItems(items);

        // Then
        assertThat(domain.getItems()).isEqualTo(asList("A", "B"));
    }
}
