package org.spoved.demo.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()

        http.headers().frameOptions().disable()

        http.cors()
                .and()
                .authorizeRequests()
        /////// SWAGGER
                //.antMatchers('/v2/api-docs', '/swagger-ui.html').hasAnyRole('ADMIN')
        ////// API 관련
                .antMatchers('/**').permitAll()

        http
                .logout()
        // /logout 을 호출할 경우 로그아웃
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        // 로그아웃이 성공했을 경우 이동할 페이지
                .logoutSuccessHandler(logoutSuccessHandler())
    }

    @Value('${spring.security.cors.allowOrigins}')
    private String allowOrigins

    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration configuration = new CorsConfiguration()
        configuration.setAllowedOrigins(Arrays.asList(allowOrigins.split(',')))
        configuration.addAllowedHeader('*')
        configuration.setAllowedMethods(Arrays.asList('GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'))
        configuration.setAllowCredentials(true)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration('/**', configuration)
        return new CorsFilter(source)
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager()
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler()
    }

    static class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
        @Override
        void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        }
    }
}
