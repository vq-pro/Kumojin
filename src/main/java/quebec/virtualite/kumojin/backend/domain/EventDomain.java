package quebec.virtualite.kumojin.backend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class EventDomain
{
    private final EventRepository repository;

    public void clear()
    {
        repository.deleteAll();
    }

    public List<String> getItems()
    {
        List<String> items = new ArrayList<>();
        for (EventModel model : repository.findAll())
        {
            items.add(model.getName());
        }

        return items;
    }

    public void setItems(List<String> items)
    {
        List<EventModel> models = items.stream()
            .map(item -> new EventModel().setName(item))
            .collect(toList());

        repository.saveAll(models);
    }
}
