package com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler;

import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.ResponseExcel;
import com.gaoice.easyexcel.writer.ExcelWriter;
import com.gaoice.easyexcel.writer.SheetInfo;
import com.gaoice.easyexcel.writer.style.DefaultSheetStyle;
import com.gaoice.easyexcel.writer.style.SheetStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * 处理 Spring MVC 框架函数带有 ResponseExcel 注解的返回值，将其解析为文件下载
 *
 * @author gaoice
 */
public class ResponseExcelReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExcelReturnValueHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ResponseExcel.class) != null;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest) throws Exception {
        /* check */
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        ResponseExcel responseExcel = methodParameter.getMethodAnnotation(ResponseExcel.class);
        Assert.state(responseExcel != null, "No @ResponseExcel");
        mavContainer.setRequestHandled(true);

        /* return value check */
        if (!(returnValue instanceof List)) {
            String msg = "return value is null or not support type, can not build excel";
            LOGGER.warn(msg);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
            response.getWriter().flush();
            return;
        }
        List<?> returnList = (List<?>) returnValue;

        /* ResponseExcel parameter check */
        String defaultString = "";
        String sheetName = responseExcel.sheetName();
        String[] fieldNames = getFieldNames(responseExcel);
        if (defaultString.equals(sheetName) || fieldNames.length == 0) {
            String msg = "not specify sheet name or fields, can not build excel";
            LOGGER.warn(msg);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
            response.getWriter().flush();
            return;
        }

        /* set sheet info */
        String title = responseExcel.title();
        String[] columnNames = responseExcel.columnNames().length == 0 ? fieldNames : responseExcel.columnNames();
        String fileName = defaultString.equals(responseExcel.fileName()) ? responseExcel.sheetName() : responseExcel.fileName();
        String fileSuffix = responseExcel.fileSuffix();
        SheetInfo sheetInfo = new SheetInfo(sheetName, title, columnNames, fieldNames, returnList);

        ResponseExcel.Node[] map = responseExcel.map();
        for (ResponseExcel.Node node : map) {
            sheetInfo.putFieldHandler(node.key(), node.value().getDeclaredConstructor().newInstance());
        }

        /* set sheet style */
        Class<?> sheetStyleClass = responseExcel.sheetStyle();
        if (sheetStyleClass != SheetStyle.class && sheetStyleClass != DefaultSheetStyle.class) {
            sheetInfo.setSheetStyle((SheetStyle) sheetStyleClass.getDeclaredConstructor().newInstance());
        }

        /* set response */
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename="
                        + URLEncoder.encode(fileName, "utf-8")
                        + fileSuffix);

        ExcelWriter.writeOutputStream(sheetInfo, response.getOutputStream());
        response.getOutputStream().flush();
    }

    private String[] getFieldNames(ResponseExcel responseExcel) {
        if (responseExcel.value().length > 0) {
            return responseExcel.value();
        }
        if (responseExcel.fieldNames().length > 0) {
            return responseExcel.fieldNames();
        }
        return responseExcel.classFieldNames();
    }
}
