package quebec.virtualite.kumojin.backend.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "event")
@Data
@Accessors(chain = true)
public class EventModel
{
    @Id
    @SequenceGenerator(name = "event_id_seq", sequenceName = "event_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_id_seq")
    private long id;

    private String name;
}
