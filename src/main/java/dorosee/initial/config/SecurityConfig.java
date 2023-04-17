package dorosee.initial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
    //WebSecurityConfigurerAdapter 추후에 제거 필요,,
    //@override -> configure
    //authenticationManager 내장


    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용 x
                .and()
                .formLogin().disable() //form login 사용 x
                .httpBasic().disable();
//                .exceptionHandling()
//                .authenticationEntryPoint(new AuthenticationEntryPointCustom())
//                .accessDeniedHandler(new AccessDeniedHandlerCustom());

        return http.build();
    }

//    @Bean
//    @Primary
//    public AuthenticationManager authenticationManager
//            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

}