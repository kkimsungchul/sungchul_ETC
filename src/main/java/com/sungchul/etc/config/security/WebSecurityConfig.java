package com.sungchul.etc.config.security;


import com.sungchul.etc.config.jwt.config.JwtAuthenticationEntryPoint;
import com.sungchul.etc.config.jwt.config.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        httpSecurity
                .csrf()
                .disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/","/csrf","/authenticate","/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**","/login","/logout","/home").permitAll()
                .antMatchers("/camping").permitAll()
                .antMatchers("/test" , "/test/**").permitAll()
                .antMatchers("/user","/user/**").hasRole("ADMIN")
                // all other requests need to be authenticated
                .anyRequest()
                .authenticated()
                .and()

                // make sure we use stateless session; session won't be used to
                // store user's state.
                //exceptionHandling() , authenticationEntryPoint(jwtAuthenticationEntryPoint) 이 두개를 추가하면 Springsecurity 의 기본 로그인 페이지를 사용 못함
                //해당 옵션을 제외하면 기본로그인 페이지를 사용할 순 있지만, 권한이 없는 페이지 또는 api 접근시에 401 메시지가 아닌 로그인 페이지를 리턴홤
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin();


        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}