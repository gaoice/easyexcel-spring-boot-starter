package com.gaoice.easyexcel.spring.boot.autoconfigure;

import com.gaoice.easyexcel.spring.boot.autoconfigure.web.EasyExcelWebMvcConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    @Bean
    public WebMvcConfigurer excelWebMvcConfigurer() {
        EasyExcelWebMvcConfigurer configurer = new EasyExcelWebMvcConfigurer();
        configurer.setEnableResponseExcel(enableResponseExcel)
                .setEnableExcelFile(enableExcelFile)
                .setEnableSheetInfo(enableSheetInfo);
        return configurer;
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
}
