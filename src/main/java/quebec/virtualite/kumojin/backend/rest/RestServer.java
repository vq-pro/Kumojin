package quebec.virtualite.kumojin.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import quebec.virtualite.kumojin.backend.domain.EventDomain;

@RequiredArgsConstructor
@RestController
public class RestServer
{
    private final EventDomain domain;

    @GetMapping("/items")
    public ResponseEntity<GetListResponse> getItems()
    {
        return ResponseEntity.ok(new GetListResponse()
            .setItems(domain.getItems()));
    }
}
