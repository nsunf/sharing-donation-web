package com.sharingdonation.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sharingdonation.config.CustomAuthenticationEntryPoint;
import com.sharingdonation.service.MemberService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
                .failureHandler(   // 로그인 실패시 자격 증명 실패 alert 출력 후 로그인 페이지로 이동
                        new AuthenticationFailureHandler() {
							@Override
							public void onAuthenticationFailure(HttpServletRequest request,HttpServletResponse response,AuthenticationException exception)
									throws IOException, ServletException {
						        response.setContentType("text/html; charset=UTF-8");
						        PrintWriter out = response.getWriter();
						        out.println("<script language='javascript'>");
						        out.println("alert('로그인에 실패하였습니다"+   "')");
						        out.println("location.href =" +'"'+ "/auth/login" + '"');
						        out.println("</script>");
						        out.flush();
							}
                        }
                )
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                .logoutSuccessUrl("/")
        ;

        http.authorizeRequests()
        .mvcMatchers("/css/**", "/js/**", "/img/**", "/assets/**").permitAll()
        .mvcMatchers("/intro", "/auth/**", "/images/**", "/lib/**").permitAll()
        .mvcMatchers("/test/**").permitAll()
        .mvcMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        ;
        
		/*
		 * http.authorizeRequests() .mvcMatchers("/css/**", "/js/**", "/img/**",
		 * "/assets/**").permitAll() .mvcMatchers("/", "/auth/**", "/images/**",
		 * "/lib/**", "/mypage/**", "/program/**", "/sharing/**", "/donation/**",
		 * "/sharing_board/**", "/donatedBoard/**").permitAll()
		 * .mvcMatchers("/test/**").permitAll()
		 * .mvcMatchers("/admin/**").hasRole("ADMIN") 
		 * .anyRequest().authenticated() ;
		 */
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