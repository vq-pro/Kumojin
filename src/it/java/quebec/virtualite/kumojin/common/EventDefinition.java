package quebec.virtualite.kumojin.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EventDefinition
{
    private String name;
    private String description;
}
