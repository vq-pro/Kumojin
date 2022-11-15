package quebec.virtualite.kumojin.backend.rest;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GetListResponse
{
    private List<String> items;
}
