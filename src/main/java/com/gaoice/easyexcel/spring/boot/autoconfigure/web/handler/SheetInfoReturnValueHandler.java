package com.gaoice.easyexcel.spring.boot.autoconfigure.web.handler;

import com.gaoice.easyexcel.writer.ExcelWriter;
import com.gaoice.easyexcel.writer.SheetInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * 处理 Spring MVC 框架 SheetInfo 类型的返回值，将其解析为文件下载
 *
 * @author gaoice
 */
public class SheetInfoReturnValueHandler implements HandlerMethodReturnValueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SheetInfoReturnValueHandler.class);

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        Method m = methodParameter.getMethod();
        return m != null && SheetInfo.class.equals(m.getReturnType());
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest) throws Exception {
        /* check */
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        Assert.state(response != null, "No HttpServletResponse");
        mavContainer.setRequestHandled(true);

        /* return value check */
        SheetInfo sheetInfo = (SheetInfo) returnValue;
        if (sheetInfo == null) {
            String msg = "return value is null, can not build excel";
            LOGGER.warn(msg);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
            response.getWriter().flush();
            return;
        }

        /* set response */
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;filename="
                        + URLEncoder.encode(sheetInfo.getSheetName(), "utf-8")
                        + ".xlsx");
        ExcelWriter.writeOutputStream(sheetInfo, response.getOutputStream());
        response.getOutputStream().flush();
    }
}