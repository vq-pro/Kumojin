package quebec.virtualite.kumojin.backend.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import quebec.virtualite.kumojin.backend.domain.EventDomain;
import quebec.virtualite.kumojin.backend.domain.EventModel;

import javax.validation.Valid;

import static java.lang.Integer.parseInt;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static quebec.virtualite.kumojin.utils.CollectionUtils.transform;
import static quebec.virtualite.kumojin.utils.DateTimeUtils.formatTimestamp;
import static quebec.virtualite.kumojin.utils.DateTimeUtils.parseTimestamp;

@RequiredArgsConstructor
@RestController
public class RestServer
{
    private static final String ERROR_PREFIX = "error ";

    private final EventDomain domain;

    @Value("${testing}")
    protected boolean isTesting;

    @PostMapping(value = "/events", consumes = "application/json")
    public ResponseEntity<Void> addEvent(@Valid @RequestBody AddEventRequest request)
    {
        if (isTesting && isErrorTrigger(request))
            return errorResponse(request);

        if (domain.exists(request.getName()))
            return ResponseEntity.status(CONFLICT).build();

        domain.addEvent(new EventModel()
            .setName(request.getName())
            .setDescription(request.getDescription())
            .setTimezone(request.getTimezone())
            .setEventStart(parseTimestamp(request.getStart(), request.getTimezone())));

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping(value = "/events", produces = "application/json")
    public ResponseEntity<GetListResponse> getEvents()
    {
        return ResponseEntity.ok(
            new GetListResponse().setEvents(
                transform(domain.getEvents(), model ->
                    new GetListResponse.Row()
                        .setName(model.getName())
                        .setDescription(model.getDescription())
                        .setStart(formatTimestamp(model.getEventStart(), model.getTimezone()))
                        .setEnd(formatTimestamp(model.getEventEnd(), model.getTimezone())))));
    }

    private ResponseEntity<Void> errorResponse(AddEventRequest request)
    {
        int errorStatus = parseInt(request.getName().substring(ERROR_PREFIX.length()));
        return ResponseEntity.status(errorStatus).build();
    }

    private boolean isErrorTrigger(AddEventRequest request)
    {
        return request.getName().startsWith(ERROR_PREFIX);
    }
}
