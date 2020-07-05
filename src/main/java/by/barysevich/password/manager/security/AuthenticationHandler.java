package by.barysevich.password.manager.security;

import by.barysevich.password.manager.exception.ServiceError;
import by.barysevich.password.manager.rest.dto.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class AuthenticationHandler implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper;

    public AuthenticationHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        var serviceError = ServiceError.UNAUTHENTICATED;
        var exceptionResponse = ExceptionResponse.builder()
                .serviceCode(serviceError.getServiceCode())
                .message(serviceError.getMessage())
                .build();
        response.setStatus(serviceError.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}
