package quebec.virtualite.kumojin.backend.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<EventModel> getEvents()
    {
        return repository.findAllByOrderByNameAsc();
    }

    public void setEvents(List<EventModel> events)
    {
        repository.saveAll(events);
    }
}
