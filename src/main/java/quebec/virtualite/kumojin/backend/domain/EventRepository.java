package quebec.virtualite.kumojin.backend.domain;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<EventModel, Long>
{
    EventModel findByName(String name);
}
