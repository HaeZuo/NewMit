package com.haezuo.newmit.config;

import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.haezuo.newmit.config.oauth.OAuth2FailureHandler;
import com.haezuo.newmit.config.oauth.OAuth2SuccessHandler;
import com.haezuo.newmit.config.oauth.OAuth2UserCustomService;
import com.haezuo.newmit.login.repository.RefreshTokenRepository;
import com.haezuo.newmit.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebOAuthSecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Bean
    public WebSecurityCustomizer configure() {
        // 웹 보안 설정
        return (web) -> web.ignoring()
                //.requestMatchers(toH2Console()) // H2 콘솔 요청은 무시
                .requestMatchers("/img/**", "/css/**", "/js/**", "/component/**", "/scripts/**", "/styles/**", "/images/**", "/fonts/**"); // 이미지, CSS, 자바스크립트 요청은 무시
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 보안 필터 설정
        http.csrf().disable() // CSRF 보호 비활성화
                .httpBasic().disable() // HTTP 기본 인증 비활성화
                .formLogin().disable() // 폼 기반 로그인 비활성화
                .logout().disable(); // 로그아웃 비활성화

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 관리 정책 설정

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // 토큰 인증 필터 추가

        http.authorizeRequests()
                .requestMatchers("/api/token").permitAll() // "/api/token" 요청은 모두 허용
                //.requestMatchers("/myPage/viewMyPage").authenticated() // 마이페이지는 인증이 필요
                .requestMatchers("/api/**").authenticated() // "/api/**" 요청은 인증 필요
                .requestMatchers("/ingredients/**").authenticated() // "/api/**" 요청은 인증 필요
                .anyRequest().permitAll(); // 나머지 요청은 모두 허용

        http.oauth2Login()
                .loginPage("/login") // 로그인 페이지 설정
                .authorizationEndpoint() // OAuth 인증 엔드포인트 설정 [ ex) /oauth2/authorization/google ]
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()) // OAuth2 권한 부여 요청 저장소 설정
                .and()
                .successHandler(oAuth2SuccessHandler()) // OAuth2 로그인 성공 핸들러 설정
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService); // 사용자 정보 엔드포인트 설정

        http.logout()
                .logoutSuccessUrl("/login"); // 로그아웃 성공 시 이동할 URL 설정

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**")); // 인증되지 않은 접근에 대한 기본 인증 진입 지점 설정

        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        
        log.debug("로그 성공");
        
        // OAuth2 로그인 성공 핸들러 설정
        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public OAuth2FailureHandler oAuth2FailureHandler() {
        // OAuth2 로그인 실패 핸들러 설정
        return new OAuth2FailureHandler();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        // 토큰 인증 필터 설정
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        // OAuth2 권한 부여 요청을 쿠키 기반으로 저장하는 설정
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // BCryptPasswordEncoder 설정
        return new BCryptPasswordEncoder();
    }
}
