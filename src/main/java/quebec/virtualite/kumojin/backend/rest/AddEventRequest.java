package quebec.virtualite.kumojin.backend.rest;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AddEventRequest
{
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Timezone is mandatory")
    private String timezone;

    @NotBlank(message = "Start is mandatory")
    private String start;

    @NotBlank(message = "End is mandatory")
    private String end;
}
