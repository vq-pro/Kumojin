package quebec.virtualite.kumojin.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import quebec.virtualite.kumojin.backend.domain.EventDomain;

import javax.validation.Valid;

import static java.lang.Integer.parseInt;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class RestServer
{
    private static final String ERROR_PREFIX = "error ";

    private final EventDomain domain;

    @PostMapping(value = "/items", consumes = "application/json")
    public ResponseEntity<Void> addItem(@Valid @RequestBody AddItemRequest request)
    {
        if (request.getName().startsWith(ERROR_PREFIX))
        {
            int errorStatus = parseInt(request.getName().substring(ERROR_PREFIX.length()));
            return ResponseEntity.status(errorStatus).build();
        }

        if (domain.exists(request.getName()))
            return ResponseEntity.status(CONFLICT).build();

        domain.addItem(request.getName());
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping(value = "/items", produces = "application/json")
    public ResponseEntity<GetListResponse> getItems()
    {
        return ResponseEntity.ok(
            new GetListResponse()
                .setItems(domain.getItems()));
    }
}
