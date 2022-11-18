package quebec.virtualite.kumojin.backend.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<EventModel, Long>
{
    List<EventModel> findAllByOrderByNameAsc();

    EventModel findByName(String name);
}
