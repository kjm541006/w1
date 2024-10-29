package study.board.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import study.board.security.CustomUserDetailsService;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class CustomSecurityConfig {

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;
    private final CustomSuccessHandler customSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        log.info("--------configure----------");

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/member/register", "/member/login", "/boards").permitAll() // 회원가입 및 로그인 페이지 접근 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
        );
        http.formLogin(config -> config
                .loginPage("/member/login")
                .permitAll()
                .successHandler(customSuccessHandler)
                .defaultSuccessUrl("/boards", true));
        http.csrf((csrf) -> csrf.disable());
        http.rememberMe(rememberMe -> rememberMe
                .key("12345678")
                .tokenRepository(persistentTokenRepository()) // 토큰 리포지토리 메서드
                .userDetailsService(userDetailsService) // UserDetailsService 설정
                .tokenValiditySeconds(60 * 60 * 24 * 30) // 유효 기간 설정
        );
        http.logout(logout -> logout
                .logoutUrl("/member/logout") // 로그아웃 URL
                .logoutSuccessUrl("/member/login?logout") // 로그아웃 성공 후 리다이렉트 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 쿠키 삭제 (필요할 경우)
        );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("-----------web configure----------");

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources().atCommonLocations());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource); // 데이터 소스 설정
        return tokenRepository;
    }

}
