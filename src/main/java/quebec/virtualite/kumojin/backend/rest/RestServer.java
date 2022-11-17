package quebec.virtualite.kumojin.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import quebec.virtualite.kumojin.backend.domain.EventDomain;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CONFLICT;

@RequiredArgsConstructor
@RestController
public class RestServer
{
    private final EventDomain domain;

    @PostMapping(value = "/items", consumes = "application/json")
    public ResponseEntity<Void> addItem(@Valid @RequestBody AddItemRequest request)
    {
        if (domain.exists(request.getName()))
            return ResponseEntity.status(CONFLICT).build();

        domain.addItem(request.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/items", produces = "application/json")
    public ResponseEntity<GetListResponse> getItems()
    {
        return ResponseEntity.ok(
            new GetListResponse()
                .setItems(domain.getItems()));
    }
}
