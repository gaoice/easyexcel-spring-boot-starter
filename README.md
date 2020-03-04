# Easyexcel Spring Boot Starter
### 概述

将 Controller 中使用 `@ResponseExcel` 注解的函数的返回值，或者类型为 `ExcelFile` 或者 `SheetInfo` 的返回值解析为 Excel 文件的下载。



### 使用

`Maven`

```xml
<dependency>
    <groupId>com.gaoice</groupId>
    <artifactId>easyexcel-spring-boot-starter</artifactId>
    <version>1.0</version>
</dependency>
```



### 配置

```properties
com.gaoice.easyexcel.enable-excel-file=true # 是否解析 ExcelFile 类型的返回值为文件下载
com.gaoice.easyexcel.enable-sheet-info=true # 是否解析 SheetInfo 类型的返回值为文件下载
com.gaoice.easyexcel.enable-response-excel=true # 是否解析带有 ResponseExcel 注解的函数的返回值为文件下载
```



### 注意事项

期望使用这个starter处理的函数不要放在使用 `@RestController` 的类中或者使用 `@ResponseBody` 修饰，否则Spring 会使用自带的返回值处理器进行处理从而导致其他返回值处理器失效。这和返回值处理器放入的顺序有关，Spring 自带的返回值处理器属于高频使用的处理器，不推荐在 Spring 的处理器前面放入自定义处理器。



### 示例

//TODO

