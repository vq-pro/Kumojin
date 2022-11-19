package quebec.virtualite.kumojin.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventTableRow
{
    private String name;
    private String description;
    private String timezone;
    private String start;
    private String end;
}
