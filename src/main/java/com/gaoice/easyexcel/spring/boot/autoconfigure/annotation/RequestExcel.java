package com.gaoice.easyexcel.spring.boot.autoconfigure.annotation;

import com.gaoice.easyexcel.reader.converter.CellConverter;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.*;
import java.util.Map;

/**
 * @author gaoice
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {

    /**
     * file name，上传的文件名字，可以理解为 {@link RequestParam#value()}
     */
    String value();

    /**
     * 是否允许上传的文件为空，可以理解为 {@link RequestParam#required()}
     */
    boolean required() default true;

    /**
     * list 开始的行号，从 0 开始计数，-1 表示自动查找 list 开始的行号，如果存在标题和表头则进行忽略
     * {@link com.gaoice.easyexcel.writer.ExcelWriter} 构建的非空的 excel 都能自动忽略标题和表头
     */
    int listFirstRowNum() default -1;

    /**
     * list 结束的行号，Integer.MAX_VALUE 表示读取至文档末尾
     * 正数举例：100，如果整个文档小于 100 行将会读取至文档末尾，否则读取至第 100 行
     * 负数举例：-1，读取至文档倒数第一行，不含倒数第一行
     */
    int listLastRowNum() default Integer.MAX_VALUE;

    /**
     * 是否忽略处理单元格值发生的异常
     */
    boolean isIgnoreException() default false;

    Class<?> targetClass() default Map.class;

    /**
     * List 的元素类型为 Map 或其子类，则不需要 fieldNames
     */
    String[] fieldNames() default {};

    /**
     * fieldName 和 CellConverter 的映射关系
     */
    Node[] map() default {};

    @interface Node {
        String key();

        Class<? extends CellConverter> value();
    }
}
