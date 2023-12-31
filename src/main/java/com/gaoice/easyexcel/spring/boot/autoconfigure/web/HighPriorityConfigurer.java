package com.gaoice.easyexcel.spring.boot.autoconfigure.web;

import com.gaoice.easyexcel.spring.boot.autoconfigure.EasyExcelAutoConfigure;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;

/**
 * @author gaoice
 * @since 2.1
 */
public class HighPriorityConfigurer implements ApplicationContextAware {

    private final EasyExcelAutoConfigure easyExcelAutoConfigure;

    private ApplicationContext applicationContext;

    public HighPriorityConfigurer(EasyExcelAutoConfigure easyExcelAutoConfigure) {
        this.easyExcelAutoConfigure = easyExcelAutoConfigure;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        if (!easyExcelAutoConfigure.isHighPriority()) {
            return;
        }
        List<HandlerMethodReturnValueHandler> easyExcelList = easyExcelAutoConfigure.handlers();
        RequestMappingHandlerAdapter adapter = applicationContext.getBean(RequestMappingHandlerAdapter.class);
        List<HandlerMethodReturnValueHandler> defaultList = adapter.getReturnValueHandlers();
        if (!CollectionUtils.isEmpty(defaultList)) {
            easyExcelList.addAll(defaultList);
        }
        adapter.setReturnValueHandlers(easyExcelList);
    }
}
