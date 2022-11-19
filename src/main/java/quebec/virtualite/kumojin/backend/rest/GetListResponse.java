package quebec.virtualite.kumojin.backend.rest;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetListResponse
{
    private List<Row> events;

    @Data
    @Accessors(chain = true)
    public static class Row
    {
        private String name;
        private String description;
        private String start;
        private String end;
    }
}
