package com.gaoice.easyexcel.spring.boot.autoconfigure;

import com.gaoice.easyexcel.spring.boot.autoconfigure.web.EasyExcelWebMvcConfigurer;
import com.gaoice.easyexcel.spring.boot.autoconfigure.web.HighPriorityConfigurer;
import com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler.ExcelFileReturnValueHandler;
import com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler.ResponseExcelReturnValueHandler;
import com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler.SheetInfoReturnValueHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaoice
 */
@Configuration
@EnableConfigurationProperties(EasyExcelAutoConfigure.class)
@ConfigurationProperties("com.gaoice.easyexcel")
public class EasyExcelAutoConfigure {

    private boolean enableExcelFile = true;
    private boolean enableSheetInfo = true;
    private boolean enableResponseExcel = true;

    private boolean highPriority = true;

    @Bean
    public WebMvcConfigurer excelWebMvcConfigurer() {
        return new EasyExcelWebMvcConfigurer(this);
    }

    @Bean
    public HighPriorityConfigurer highPriorityConfigurer() {
        return new HighPriorityConfigurer(this);
    }

    public List<HandlerMethodReturnValueHandler> handlers() {
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>();
        if (enableExcelFile) {
            handlers.add(new ExcelFileReturnValueHandler());
        }
        if (enableSheetInfo) {
            handlers.add(new SheetInfoReturnValueHandler());
        }
        if (enableResponseExcel) {
            handlers.add(new ResponseExcelReturnValueHandler());
        }
        return handlers;
    }

    public boolean isEnableResponseExcel() {
        return enableResponseExcel;
    }

    public void setEnableResponseExcel(boolean enableResponseExcel) {
        this.enableResponseExcel = enableResponseExcel;
    }

    public boolean isEnableExcelFile() {
        return enableExcelFile;
    }

    public void setEnableExcelFile(boolean enableExcelFile) {
        this.enableExcelFile = enableExcelFile;
    }

    public boolean isEnableSheetInfo() {
        return enableSheetInfo;
    }

    public void setEnableSheetInfo(boolean enableSheetInfo) {
        this.enableSheetInfo = enableSheetInfo;
    }

    public boolean isHighPriority() {
        return highPriority;
    }

    public void setHighPriority(boolean highPriority) {
        this.highPriority = highPriority;
    }
}
