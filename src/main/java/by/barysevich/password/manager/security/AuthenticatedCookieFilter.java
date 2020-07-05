package by.barysevich.password.manager.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticatedCookieFilter extends OncePerRequestFilter {

    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String COOKIE_NAME = "Authenticated";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        response.addCookie(getAuthenticatedCookie(request));
        filterChain.doFilter(request, response);
    }

    private Cookie getAuthenticatedCookie(HttpServletRequest request) {
        var cookie = new Cookie(COOKIE_NAME, Boolean.TRUE.toString());
        cookie.setPath(request.getContextPath() + "/");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(ROLE_ANONYMOUS))) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
        }
        return cookie;
    }
}
