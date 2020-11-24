package com.gaoice.easyexcel.spring.boot.autoconfigure.web.resolver;

import com.gaoice.easyexcel.reader.ExcelReader;
import com.gaoice.easyexcel.reader.SheetResult;
import com.gaoice.easyexcel.reader.sheet.BeanConfig;
import com.gaoice.easyexcel.reader.sheet.SheetConfig;
import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.RequestExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author gaoice
 */
public class RequestExcelArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestExcelArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestExcel.class) != null;
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestExcel requestExcel = parameter.getParameterAnnotation(RequestExcel.class);
        Assert.state(requestExcel != null, "No @RequestExcel");
        boolean required = requestExcel.required();
        MultipartRequest request = webRequest.getNativeRequest(MultipartRequest.class);
        if (request == null) {
            LOGGER.warn("No MultipartRequest");
            if (required) {
                throw new NullPointerException();
            }
            return null;
        }
        MultipartFile multipartFile = request.getFile(requestExcel.value());
        if (multipartFile == null) {
            LOGGER.warn("No MultipartFile");
            if (required) {
                throw new NullPointerException();
            }
            return null;
        }

        Class<?> targetClass = requestExcel.targetClass();

        if (List.class.isAssignableFrom(parameter.getParameterType())) {
            if (Map.class.isAssignableFrom(targetClass)) {
                return ExcelReader.parseList(multipartFile.getInputStream(), getSheetConfig(requestExcel));
            } else {
                return ExcelReader.parseList(multipartFile.getInputStream(), getBeanConfig(requestExcel));
            }
        } else if (SheetResult.class.isAssignableFrom(parameter.getParameterType())) {
            if (Map.class.isAssignableFrom(targetClass)) {
                return ExcelReader.parseSheetResult(multipartFile.getInputStream(), getSheetConfig(requestExcel));
            } else {
                return ExcelReader.parseSheetResult(multipartFile.getInputStream(), getBeanConfig(requestExcel));
            }
        } else {
            String s = "@RequestExcel not support " + parameter.getParameterType().getName();
            LOGGER.warn(s);
            if (required) {
                throw new IllegalArgumentException(s);
            }
            return null;
        }
    }

    private SheetConfig getSheetConfig(RequestExcel requestExcel) {
        int listFirstRowNum = requestExcel.listFirstRowNum();
        int listLastRowNum = requestExcel.listLastRowNum();
        boolean isIgnoreException = requestExcel.isIgnoreException();
        return new SheetConfig().setListFirstRowNum(listFirstRowNum)
                .setListLastRowNum(listLastRowNum)
                .setIgnoreException(isIgnoreException);
    }

    @SuppressWarnings("unchecked")
    private BeanConfig<?> getBeanConfig(RequestExcel requestExcel) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int listFirstRowNum = requestExcel.listFirstRowNum();
        int listLastRowNum = requestExcel.listLastRowNum();
        boolean isIgnoreException = requestExcel.isIgnoreException();
        Class<?> targetClass = requestExcel.targetClass();
        String[] filedNames = requestExcel.fieldNames();
        BeanConfig<?> beanConfig = new BeanConfig<>().setListFirstRowNum(listFirstRowNum)
                .setListLastRowNum(listLastRowNum)
                .setIgnoreException(isIgnoreException)
                .setTargetClass((Class<Object>) targetClass)
                .setFieldNames(filedNames);

        RequestExcel.Node[] map = requestExcel.map();
        for (RequestExcel.Node node : map) {
            beanConfig.putConverter(node.key(), node.value().getDeclaredConstructor().newInstance());
        }

        return beanConfig;
    }
}
