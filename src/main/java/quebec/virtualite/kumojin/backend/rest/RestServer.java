package quebec.virtualite.kumojin.backend.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Arrays.asList;

@RestController
public class RestServer
{
    @GetMapping("/items")
    public ResponseEntity<GetListResponse> getItems()
    {
        return ResponseEntity.ok(new GetListResponse()
            .setItems(asList("Apple", "Banana")));
    }
}
