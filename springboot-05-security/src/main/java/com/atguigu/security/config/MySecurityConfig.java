package com.atguigu.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity       //这个注解里面已经代理@Configuration注解，所以不需要在该类上加上@Configuration注解
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);

        //定制http请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置的登录功能
        http.formLogin().usernameParameter("user").passwordParameter("pwd").loginPage("/userlogin");
        //对应的.usernameParameter("自己的登录页的表单字段name")

        //1、/login来到登录页,默认是定向到框架的登陆页面，现在也可以定向到自己的登陆页面,通过.loginPage("/login")
        //注意，除了指定登陆的页面，还需要指定登录的表单数据发送到哪【只需要将自己的登录页表单发送post请求即可】
        //2、重定向到/login?error表示登陆失败

        //开启自动配置的注销功能
        http.logout().logoutSuccessUrl("/");//注销成功来到首页
        //访问/logout表示用户注销，清空session，注销成功会返回/login?logout

        //开启记住我的功能
        http.rememberMe().rememberMeParameter("remeber");
        //登录成功之后，将cookie发给浏览器保存，以后登陆带上这个cookie，只要通过检查就可以
//        如果点击注销会删除cookie

    }

    //定义认证规则


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        //认证默认是需要连接数据库
//springboot2.x粗腰进行密码加密，否则报错  https://blog.csdn.net/syc000666/article/details/96862574
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP2")
                .and()
                .withUser("lishi").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1","VIP3");


    }
}
