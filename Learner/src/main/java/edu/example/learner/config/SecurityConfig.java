package edu.example.learner.config;

import edu.example.learner.member.service.CustomOauth2UserService;
import edu.example.learner.security.filter.JWTCheckFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private final JWTCheckFilter jwtCheckFilter;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomOauth2UserService customOauth2UserService;


    public SecurityConfig(JWTCheckFilter jwtCheckFilter, CustomOauth2UserService customOauth2UserService, CustomSuccessHandler  customSuccessHandler) {
        this.jwtCheckFilter = jwtCheckFilter;
        this.customOauth2UserService = customOauth2UserService;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomClientRegistrationRepo customClientRegistrationRepo) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/members/**").authenticated()
                        .requestMatchers("/my").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/members/other").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login.disable())
                .logout(logout -> logout.permitAll())
                .oauth2Login((oauth2) ->
                        oauth2.userInfoEndpoint((userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOauth2UserService)))
                                .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                                .successHandler(customSuccessHandler)
                );

        //JWTCheckFilter 필터 추가
        http.addFilterBefore(jwtCheckFilter,
                UsernamePasswordAuthenticationFilter.class);

        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
        });

        return http.build();
    }

    //CORS ; Cross Origin Resource Sharing설정 관련 처리 ---------------------------------------
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfig = new CorsConfiguration();

        //접근 패턴 - 모든 출처에서의 요청 허락
        corsConfig.setAllowedOriginPatterns(List.of("*"));

        corsConfig.setAllowedMethods(           //허용 메서드
                List.of("GET", "POST", "PUT", "DELETE"));

        corsConfig.setAllowedHeaders(           //허용 헤더
                List.of("Authorization",
                        "Content-Type",
                        "Cache-Control"));

        corsConfig.setAllowCredentials(true);   //자격 증명 허용 여부

        //URL 패턴 기반으로 CORS 구성
        UrlBasedCorsConfigurationSource corsSource
                = new UrlBasedCorsConfigurationSource();

        corsSource.registerCorsConfiguration("/**",    //모든 경로 적용
                corsConfig);

        return corsSource;
    }
}
