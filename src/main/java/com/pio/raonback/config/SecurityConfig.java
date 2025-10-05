package com.pio.raonback.config;

import com.pio.raonback.security.RestAccessDeniedHandler;
import com.pio.raonback.security.jwt.JwtAuthenticationFilter;
import com.pio.raonback.security.jwt.JwtAuthenticationProvider;
import com.pio.raonback.security.jwt.SkipPathRequestMatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final RequestMatcher WEBSOCKET_CONNECT_REQUEST_MATCHER =
      PathPatternRequestMatcher.withDefaults().matcher("/ws");
  private static final RequestMatcher JWT_BASED_AUTH_REQUEST_MATCHER =
      PathPatternRequestMatcher.withDefaults().matcher("/api/v1/**");
  private static final OrRequestMatcher NON_JWT_BASED_AUTH_REQUEST_MATCHERS = new OrRequestMatcher(
      PathPatternRequestMatcher.withDefaults().matcher("/api/v1/auth/**"),
      PathPatternRequestMatcher.withDefaults().matcher("/api/v1/locations/**"),
      PathPatternRequestMatcher.withDefaults().matcher("/api/v1/categories/**"),
      PathPatternRequestMatcher.withDefaults().matcher("/api/v1/users/**"),
      PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/v1/products"),
      PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/api/v1/products/{productId}"),
      PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.PATCH, "/api/v1/products/{productId}/views")
  );

  private final AuthenticationFailureHandler failureHandler;
  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  @Bean
  public AuthenticationManager authenticationManager(ObjectPostProcessor<Object> objectPostProcessor) throws Exception {
    DefaultAuthenticationEventPublisher eventPublisher =
        objectPostProcessor.postProcess(new DefaultAuthenticationEventPublisher());
    AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
    builder.authenticationEventPublisher(eventPublisher);
    builder.authenticationProvider(jwtAuthenticationProvider);
    return builder.build();
  }

  protected JwtAuthenticationFilter buildJwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    SkipPathRequestMatcher matcher =
        new SkipPathRequestMatcher(NON_JWT_BASED_AUTH_REQUEST_MATCHERS, JWT_BASED_AUTH_REQUEST_MATCHER);
    JwtAuthenticationFilter filter = new JwtAuthenticationFilter(failureHandler, matcher);
    filter.setAuthenticationManager(authenticationManager);
    return filter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.formLogin(AbstractHttpConfigurer::disable);
    http.httpBasic(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests((auth) -> auth
        .requestMatchers(WEBSOCKET_CONNECT_REQUEST_MATCHER).permitAll()
        .requestMatchers(NON_JWT_BASED_AUTH_REQUEST_MATCHERS).permitAll()
        .anyRequest().authenticated());
    http.sessionManagement((session) -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.addFilterAt(buildJwtAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);
    http.exceptionHandling((exception) -> exception
        .accessDeniedHandler(new RestAccessDeniedHandler()));
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
