package quebec.virtualite.kumojin.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import quebec.virtualite.kumojin.backend.domain.EventDomain;

@RequiredArgsConstructor
@RestController
public class RestServer
{
    private final EventDomain domain;

    @PostMapping(value = "/items", consumes = "application/json")
    public ResponseEntity<Void> addItem(@RequestBody AddItemRequest request)
    {
        domain.addItem(request.getName());
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/items", produces = "application/json")
    public ResponseEntity<GetListResponse> getItems()
    {
        return ResponseEntity.ok(
            new GetListResponse()
                .setItems(domain.getItems()));
    }
}
