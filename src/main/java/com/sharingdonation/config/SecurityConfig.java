package com.sharingdonation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sharingdonation.config.CustomAuthenticationEntryPoint;
import com.sharingdonation.service.MemberService;


@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	 

	@Autowired
    MemberService memberService;
	

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/auth/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/auth/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/")
        ;

        http.authorizeRequests()
                .mvcMatchers("/css/**", "/js/**", "/img/**", "/assets/**").permitAll()
                .mvcMatchers("/", "/auth/**", "/images/**", "/lib/**", "/mypage/**", "/program/**", "/sharing/**", "/donation/**", "/sharing_board/**", "/donatedBoard/**").permitAll()
                .mvcMatchers("/test/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	
	/*
	//오버라이딩을 통해 보안 설정을 커스터마이징 한다.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
        .loginPage("/members/login")
        .defaultSuccessUrl("/")
        .usernameParameter("email")
        .failureUrl("/members/login/error")
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
        .logoutSuccessUrl("/");
        
        http.authorizeRequests()
        .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
        .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
        .mvcMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated();

        http.exceptionHandling()
        .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
	}
	
	//비밀번호를 암호화 해서 저장
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService)
		    .passwordEncoder(passwordEncoder());
	}
	*/
}