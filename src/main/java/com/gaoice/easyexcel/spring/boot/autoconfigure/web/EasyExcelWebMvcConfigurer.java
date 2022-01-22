package com.gaoice.easyexcel.spring.boot.autoconfigure.web;

import com.gaoice.easyexcel.spring.boot.autoconfigure.EasyExcelAutoConfigure;
import com.gaoice.easyexcel.spring.boot.autoconfigure.web.resolver.RequestExcelArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author gaoice
 */
public class EasyExcelWebMvcConfigurer implements WebMvcConfigurer {

    private final EasyExcelAutoConfigure easyExcelAutoConfigure;

    public EasyExcelWebMvcConfigurer(EasyExcelAutoConfigure easyExcelAutoConfigure) {
        this.easyExcelAutoConfigure = easyExcelAutoConfigure;
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        if (easyExcelAutoConfigure.isHighPriority()) {
            return;
        }
        handlers.addAll(easyExcelAutoConfigure.handlers());
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestExcelArgumentResolver());
    }
}
