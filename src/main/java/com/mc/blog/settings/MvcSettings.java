package com.mc.blogadmin.settings;

import com.mc.blogadmin.formatter.IdTypeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcSettings implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/view/addArticle").setViewName("/blog/addArticle");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new IdTypeFormatter());
    }
}
