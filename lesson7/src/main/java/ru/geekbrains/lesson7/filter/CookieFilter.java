package ru.geekbrains.lesson7.filter;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class CookieFilter extends GenericFilterBean {

    public static final String ANON_CART_COOKIE_NAME = "anon-cart-id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        checkCookie(request, response, principal);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void checkCookie(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Cookie cookie = Arrays.stream(Objects.requireNonNullElse(request.getCookies(), new Cookie[0]))
                .filter(c -> c.getName().equalsIgnoreCase(ANON_CART_COOKIE_NAME))
                .findFirst()
                .orElse(null);

        if (principal instanceof AnonymousAuthenticationToken) {
            if (cookie == null) {
                cookie = new Cookie(ANON_CART_COOKIE_NAME, UUID.randomUUID().toString());
                cookie.setPath("/lesson7");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(900);
                response.addCookie(cookie);
            }
        }
    }
}
