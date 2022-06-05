package com.nhnacademy.security.config;

import com.nhnacademy.security.auth.LoginSuccessHandler;
import com.nhnacademy.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/private-project/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MEMBER")
                .antMatchers("/project/**").authenticated()
                .antMatchers("/redirect-index").authenticated()
                .anyRequest().permitAll()
                .and()
            .requiresChannel()
                .antMatchers("/admin/**").requiresSecure()
                .antMatchers("/private-project/**").requiresSecure()
                .antMatchers("/project/**").requiresSecure()
                .anyRequest().requiresInsecure()
                .and()
            .oauth2Login()
                // TODO : #4 실습 - clientRegistrationRepository와 authorizedClientService를 설정해주세요.
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .and()
/*
            .formLogin()
                .usernameParameter("id")
                .passwordParameter("pwd")
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler(null))
                .and()
 */
            .logout()
                .and()
            .csrf()
                .and()
            .sessionManagement()
                .sessionFixation()
                    .none()
                .and()
            .headers()
                .defaultsDisabled()
                .frameOptions().sameOrigin()
                .and()
            .exceptionHandling()
                .accessDeniedPage("/error/403")
                .and();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider(null));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(RedisTemplate<String, String> redisTemplate) {
        return new LoginSuccessHandler(redisTemplate);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        // TODO : #2 실습 - ClientRegistrationRepository 구현체를 생성하세요.
        //        아래 github() 메서드를 활용하세요.
        return new InMemoryClientRegistrationRepository(github());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        // TODO : #3 실습 - OAuth2AuthorizedClientService 구현체를 생성하세요.
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    private ClientRegistration github() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
            .userNameAttributeName("name")
            // TODO #1: github에서 생성한 어플리케이션 정보를 참조해서 client_id와 client_secret을 등록하세요.
                .clientId("bbef5a7961bd9535bae8")
                .clientSecret("cdd39ab42f0b05bafb5620dd13fa78b201d20653")
            .build();
    }

}
