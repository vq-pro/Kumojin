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

    public void addItem(String name)
    {
        repository.save(
            new EventModel()
                .setName(name));
    }

    public void clear()
    {
        repository.deleteAll();
    }

    public boolean exists(String name)
    {
        return repository.findByName(name) != null;
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
