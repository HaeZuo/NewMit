package com.haezuo.newmit;

import com.haezuo.newmit.config.layout.SiteMeshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄링 Enable
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class NewMitApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NewMitApplication.class);
        //app.addListeners();
        app.run(args);
    }

    @Bean
    public FilterRegistrationBean siteMeshFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new SiteMeshFilter());

        return filter;
    }

}
