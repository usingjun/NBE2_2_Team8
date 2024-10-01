package edu.example.learner.config;

import edu.example.learner.member.dto.Oauth2.CustomOauth2User;
import edu.example.learner.security.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //OAuth2User
        CustomOauth2User user = (CustomOauth2User) authentication.getPrincipal();

        Map<String, Object> claims = user.getAttributes();

        Collection<? extends GrantedAuthority> userRole = user.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = userRole.iterator();

        claims.put("role", iterator.next().getAuthority());

        String token = jwtUtil.createToken(claims,30);

        response.addHeader("Authorization", "Bearer " + token);
    }

    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
