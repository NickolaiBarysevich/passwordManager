package by.barysevich.password.manager.logging;

import by.barysevich.password.manager.exception.ServiceException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* by..*rest.CredentialsController.*(..))", throwing = "ex")
    public void logCredentialsController(Throwable ex) {
        log(ex);
    }

    private void log(Throwable ex) {
        if (ex instanceof ServiceException) {
            var cause = ex.getCause();
            if (Objects.isNull(cause) && LOGGER.isDebugEnabled()) {
                LOGGER.debug(ex.getMessage());
            } else {
                LOGGER.error(cause.getMessage(), cause);
            }
        } else {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @AfterThrowing(pointcut = "execution(* by..*rest.UserController.*(..))", throwing = "ex")
    public void logUserController(Throwable ex) {
        log(ex);
    }
}
