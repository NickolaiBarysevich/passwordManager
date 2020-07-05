package by.barysevich.password.manager.rest;

import by.barysevich.password.manager.rest.dto.SignUpRequest;
import by.barysevich.password.manager.service.api.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }
}
