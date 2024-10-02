package edu.example.learner.security.filter;

import edu.example.learner.security.auth.CustomUserPrincipal;
import edu.example.learner.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("--- doFilterInternal() ");
        log.info("--- requestURI : " + request.getRequestURI());

        // 제외할 경로 배열
        String[] shouldNotPath = {"/api/", "/join", "/login"}; // 배열에 모든 경로 추가

        // shouldNotPath 배열과 비교
        for (String path : shouldNotPath) {
            if (request.getRequestURI().startsWith(path)) { // startsWith 사용
                log.info("--- Skipping JWT verification for path: " + request.getRequestURI());
                filterChain.doFilter(request, response); // 다음 필터로 요청 전달
                return; // JWT 검증 로직을 실행하지 않음
            }
        }

        String authrization = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    authrization = cookie.getValue();
                }
            }
        } else {
            log.info("--- No cookies found");
        }

        log.info("--- authrization : " + authrization);


        if( authrization == null) {
            handleException(response,
                    new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }


        //토큰 유효성 검증 --------------------------------------
        String accessToken = authrization;

        try {
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            log.info("--- 토큰 유효성 검증 완료 ---");

            //SecurityContext 처리 -----------------------------------------
            String mid = claims.get("mid").toString();
            String role = claims.get("role").toString(); //단일 역할 처리

            //토큰을 이용하여 인증된 정보 저장
            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(
                    new CustomUserPrincipal(mid, role),
                    null, List.of(new SimpleGrantedAuthority("ROLE_" + role))
            );

            //SecurityContext에 인증/인가 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
            //검증 결과 문제가 없는 경우
            //이 코드가 호출되기 전까지는, 현재 필터에서 JWT를 검증하고 사용자 정보를 설정하는 과정을 수행합니다.
            //이 코드가 실행되면, JWT 검증을 성공적으로 마친 후, 요청이 다음 필터 또는 해당 컨트롤러로 전달됩니다.
        } catch (Exception e) {
            handleException(response, e);             //예외가 발생한 경우
        }
    }

    public void handleException(HttpServletResponse response, Exception e)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter()
                .println("{\"error\": \"" + e.getMessage() + "\"}");
    }
}
