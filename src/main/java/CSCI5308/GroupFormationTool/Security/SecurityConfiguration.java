package CSCI5308.GroupFormationTool.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/*
 * This code comes from this tutorial:
 * https://dzone.com/articles/add-login-to-your-spring-boot-app-in-10-mins
 *
 * I'm going to make it so that every page on the site requires authentication. I will use the Spring
 * security mechanisms to enforce this. If a user is not authenticated this is the class that will
 * redirect them somewhere to login/sign up.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/password/**").permitAll()
                .antMatchers("/styles/**").permitAll()
                .antMatchers("/signup/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .and().logout()
                .permitAll();

    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return new CustomAuthenticationManager();
    }

}
