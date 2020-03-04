package com.gaoice.easyexcel.spring.boot.autoconfigure.annotation;

import com.gaoice.easyexcel.style.DefaultSheetStyle;
import com.gaoice.easyexcel.style.SheetStyle;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {

    String sheetName() default "default";

    String title() default "";

    String[] columnNames() default {};

    String[] classFieldNames();

    Class<? extends SheetStyle> sheetStyle() default DefaultSheetStyle.class;

    String fileName() default "";

    String fileSuffix() default ".xlsx";
}
