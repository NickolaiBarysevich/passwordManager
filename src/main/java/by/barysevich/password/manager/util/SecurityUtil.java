package by.barysevich.password.manager.util;

import by.barysevich.password.manager.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

    private SecurityUtil() {

    }

    public static int getCurrentUserId() {
        return ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
