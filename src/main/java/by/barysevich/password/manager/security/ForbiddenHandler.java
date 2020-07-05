package by.barysevich.password.manager.security;

import by.barysevich.password.manager.exception.ServiceError;
import by.barysevich.password.manager.rest.dto.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class ForbiddenHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper;

    public ForbiddenHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        var serviceError = ServiceError.ACCESS_DENIED;
        var exceptionResponse = ExceptionResponse.builder()
                .serviceCode(serviceError.getServiceCode())
                .message(serviceError.getMessage())
                .build();
        response.setStatus(serviceError.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}
