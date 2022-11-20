package quebec.virtualite.kumojin.backend.rest;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Accessors(chain = true)
public class AddEventRequest
{
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Timezone is mandatory")
    @Pattern(regexp = "[+-][0-9]{2}:[0-9]{2}")
    private String timezone;

    @NotBlank(message = "Start is mandatory")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")
    private String start;

    @NotBlank(message = "End is mandatory")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")
    private String end;
}
