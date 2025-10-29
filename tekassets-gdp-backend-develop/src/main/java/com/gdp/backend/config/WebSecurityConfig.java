package com.gdp.backend.config;

import com.gdp.backend.auth.JwtAuthenticationEntryPoint;
import com.gdp.backend.auth.JwtAuthenticationTokenFilter;
import com.gdp.backend.common.Utils;
import com.gdp.backend.repository.GDPUserRepository;
import com.gdp.backend.repository.ProjectsRepository;
import com.gdp.backend.repository.ResourceRepository;
import com.gdp.backend.service.GDPUserDetailsService;
import com.gdp.backend.util.AzureADUtils;
import com.gdp.backend.util.CurrentUsernameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,
        jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CurrentUsernameUtil currentUsernameUtil;

    @Resource
    private AzureADUtils azureADUtils;

    @Autowired
    private GDPUserRepository gdpUserRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Override
    public GDPUserDetailsService userDetailsServiceBean() {
        return new GDPUserDetailsService(gdpUserRepository,resourceRepository);
    }

    @Bean
    public Utils utilsBean(){
        return new Utils(currentUsernameUtil,gdpUserRepository,projectsRepository);
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationTokenFilter(userDetailsServiceBean(), azureADUtils);
    }

    @Value("#{${authWhiteList}}")
    private List<String> AUTH_WHITELIST;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //cors
        http.cors();

        //X-Frame-Options enable
        http.headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable();

        //AZURE TOKEN
        http
                .csrf().disable()
                .authorizeRequests().antMatchers(AUTH_WHITELIST.toArray(new String[0])).permitAll().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().anyRequest().fullyAuthenticated();

        // Custom JWT based security filter
        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }
}