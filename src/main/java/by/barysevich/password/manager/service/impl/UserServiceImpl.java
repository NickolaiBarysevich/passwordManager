package by.barysevich.password.manager.service.impl;

import by.barysevich.password.manager.exception.ServiceException;
import by.barysevich.password.manager.model.User;
import by.barysevich.password.manager.repository.api.Repository;
import by.barysevich.password.manager.repository.api.SqlConstants;
import by.barysevich.password.manager.repository.specification.FindUserByUsernameSpecification;
import by.barysevich.password.manager.rest.dto.SignUpRequest;
import by.barysevich.password.manager.security.UserPrincipal;
import by.barysevich.password.manager.service.api.UserService;
import by.barysevich.password.manager.service.validator.StringValidator;
import by.barysevich.password.manager.service.validator.Validator;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    /*language=REGEXP*/
    public static final String PHONE_REGEXP = "\\+\\d{1,3}\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";

    private BCryptPasswordEncoder passwordEncoder;
    private Repository<User, Integer> repository;

    /**
     * Constructs user service.
     * @param passwordEncoder for user password
     * @param repository for fetching data from db
     */
    public UserServiceImpl(BCryptPasswordEncoder passwordEncoder,
                           @Qualifier("userRepository") Repository<User, Integer> repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public void signUp(SignUpRequest request) {
        signUpRequestValidator().validate(request);
        repository.insert(toUser(request));
    }

    private Validator<SignUpRequest> signUpRequestValidator() {
        return request -> {
            StringValidator.getInstance(request.getUsername())
                    .notBlank()
                    .min(3)
                    .max(64)
                    .parameterName(SqlConstants.USERNAME)
                    .exceptionMessage("must be not blank and between 3 and 64 digits")
                    .validate();

            StringValidator.getInstance(request.getPassword())
                    .notBlank()
                    .min(8)
                    .max(256)
                    .parameterName(SqlConstants.PASSWORD)
                    .exceptionMessage("must be not blank and more than 8 digits")
                    .validate();

            if (!EmailValidator.getInstance(Boolean.TRUE).isValid(request.getEmail())) {
                throw ServiceException.badParameter("email", "email is bad formed");
            }
        };
    }

    private User toUser(SignUpRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var specification = new FindUserByUsernameSpecification(username);
        var user = repository.queryForOne(specification).orElseThrow(() -> new UsernameNotFoundException(username));
        return UserPrincipal.fromUser(user);
    }
}
