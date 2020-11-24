package com.gaoice.easyexcel.spring.boot.autoconfigure.annotation;

import com.gaoice.easyexcel.writer.handler.FieldHandler;
import com.gaoice.easyexcel.writer.style.DefaultSheetStyle;
import com.gaoice.easyexcel.writer.style.SheetStyle;

import java.lang.annotation.*;

/**
 * @author gaoice
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {

    String sheetName() default "default";

    String title() default "";

    /**
     * columnNames 为空的时候，将会使用 fieldNames
     */
    String[] columnNames() default {};

    /**
     * 此处的 value 指的是 fieldNames
     *
     * @see #fieldNames()
     */
    String[] value() default {};

    /**
     * @since 1.1
     */
    String[] fieldNames() default {};

    /**
     * classFieldNames 名称已过时，最新的名称为 fieldNames
     * 此版本中 classFieldNames 在 fieldNames 为空的情况下仍然生效。
     *
     * @see #fieldNames()
     */
    @Deprecated
    String[] classFieldNames() default {};

    Class<? extends SheetStyle> sheetStyle() default DefaultSheetStyle.class;

    /**
     * fileName 为空的时候将会使用 sheetName
     */
    String fileName() default "";

    String fileSuffix() default ".xlsx";

    /**
     * fieldName 和 FieldHandler 的映射关系
     */
    Node[] map() default {};

    @interface Node {
        String key();

        Class<? extends FieldHandler<?>> value();
    }
}
