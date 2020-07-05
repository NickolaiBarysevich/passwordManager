package by.barysevich.password.manager.service.api;

import by.barysevich.password.manager.rest.dto.SignUpRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void signUp(SignUpRequest request);

}
