package com.gaoice.easyexcel.spring.boot.autoconfigure.web;

import com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler.ExcelFileReturnValueHandler;
import com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler.ResponseExcelReturnValueHandler;
import com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler.SheetInfoReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class EasyExcelWebMvcConfigurer implements WebMvcConfigurer {

    private boolean enableResponseExcel = true;
    private boolean enableExcelFile = true;
    private boolean enableSheetInfo = true;

    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        if (enableExcelFile) {
            handlers.add(new ExcelFileReturnValueHandler());
        }
        if (enableSheetInfo) {
            handlers.add(new SheetInfoReturnValueHandler());
        }
        if (enableResponseExcel) {
            handlers.add(new ResponseExcelReturnValueHandler());
        }
    }

    public boolean isEnableResponseExcel() {
        return enableResponseExcel;
    }

    public EasyExcelWebMvcConfigurer setEnableResponseExcel(boolean enableResponseExcel) {
        this.enableResponseExcel = enableResponseExcel;
        return this;
    }

    public boolean isEnableExcelFile() {
        return enableExcelFile;
    }

    public EasyExcelWebMvcConfigurer setEnableExcelFile(boolean enableExcelFile) {
        this.enableExcelFile = enableExcelFile;
        return this;
    }

    public boolean isEnableSheetInfo() {
        return enableSheetInfo;
    }

    public EasyExcelWebMvcConfigurer setEnableSheetInfo(boolean enableSheetInfo) {
        this.enableSheetInfo = enableSheetInfo;
        return this;
    }
}
