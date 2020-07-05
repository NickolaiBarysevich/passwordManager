package by.barysevich.password.manager.rest;

import by.barysevich.password.manager.rest.dto.CreateCredentialsRequest;
import by.barysevich.password.manager.rest.dto.CredentialsResponse;
import by.barysevich.password.manager.rest.dto.ShortCredentialsResponse;
import by.barysevich.password.manager.service.api.CredentialsService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/credentials",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class CredentialsController {

    private CredentialsService service;

    public CredentialsController(CredentialsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ShortCredentialsResponse>> getUserShortCredentials() {
        return ResponseEntity.ok(service.getUserShortCredentials());
    }

    @PostMapping
    public ResponseEntity<Void> createCredentials(@RequestBody CreateCredentialsRequest request) {
        service.createCredentials(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateCredentials(@PathVariable String id,
                                                     @RequestBody CreateCredentialsRequest request) {
        return ResponseEntity.ok(service.updateCredentials(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteCredentials(@PathVariable String id) {
        return ResponseEntity.ok(service.deleteCredentials(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredentialsResponse> getCredentialsById(@PathVariable String id) {
        return ResponseEntity.ok(service.getCredentialsById(id));
    }
}
