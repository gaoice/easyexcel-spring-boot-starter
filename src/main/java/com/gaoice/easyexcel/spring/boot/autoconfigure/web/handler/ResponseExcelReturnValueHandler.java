package com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler;

import com.gaoice.easyexcel.ExcelBuilder;
import com.gaoice.easyexcel.SheetInfo;
import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.ResponseExcel;
import com.gaoice.easyexcel.style.DefaultSheetStyle;
import com.gaoice.easyexcel.style.SheetStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * 处理 Spring MVC 框架函数带有 ResponseExcel 注解的返回值，将其解析为文件下载
 */
public class ResponseExcelReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExcelReturnValueHandler.class);

    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ResponseExcel.class) != null;
    }

    public void handleReturnValue(@Nullable Object o, MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest) throws Exception {
        /* check */
        HttpServletResponse response = (HttpServletResponse) nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        ResponseExcel responseExcel = methodParameter.getMethodAnnotation(ResponseExcel.class);
        Assert.state(responseExcel != null, "No @ResponseExcel");
        mavContainer.setRequestHandled(true);

        /* return value check */
        if (!(o instanceof List)) {
            String msg = "return value is null or not support type, can not build excel";
            LOGGER.warn(msg);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
            response.getWriter().flush();
            return;
        }
        List list = (List) o;

        /* ResponseExcel parameter check */
        String defaultString = "";
        String sheetName = responseExcel.sheetName();
        String[] classFieldNames = responseExcel.classFieldNames();
        if (defaultString.equals(sheetName) || classFieldNames.length == 0) {
            String msg = "not specify sheet name or fields, can not build excel";
            LOGGER.warn(msg);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
            response.getWriter().flush();
            return;
        }

        /* set sheet info */
        String title = responseExcel.title();
        String[] columnNames = responseExcel.columnNames().length == 0 ?
                responseExcel.classFieldNames() : responseExcel.columnNames();
        String fileName = responseExcel.fileName().equals(defaultString) ?
                responseExcel.sheetName() : responseExcel.fileName();
        String fileSuffix = responseExcel.fileSuffix();
        SheetInfo sheetInfo;
        if (defaultString.equals(title)) {
            sheetInfo = new SheetInfo(sheetName, columnNames, classFieldNames, list);
        } else {
            sheetInfo = new SheetInfo(sheetName, title, columnNames, classFieldNames, list);
        }

        /* set sheet style */
        Class sheetStyleClass = responseExcel.sheetStyle();
        if (sheetStyleClass != SheetStyle.class
                && sheetStyleClass != DefaultSheetStyle.class) {
            sheetInfo.setSheetStyle((SheetStyle) sheetStyleClass.newInstance());
        }

        /* set response */
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition"
                , "attachment;filename="
                        + URLEncoder.encode(fileName, "utf-8")
                        + fileSuffix);

        ExcelBuilder.writeOutputStream(sheetInfo, response.getOutputStream());
        response.getOutputStream().flush();
    }
}
