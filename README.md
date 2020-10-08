# Easyexcel Spring Boot Starter
## 概述

将 Controller 中使用 `@ResponseExcel` 注解的函数的返回值，或者类型为 `ExcelFile` 或者 `SheetInfo` 的返回值解析为 Excel 文件下载。



## 使用

已提交至 `Maven` 中央仓库。

`Maven`

```xml
<dependency>
    <groupId>com.gaoice</groupId>
    <artifactId>easyexcel-spring-boot-starter</artifactId>
    <version>1.1</version>
</dependency>
```



## 新版本

- v 1.1，支持 `List<Map<?,?>>` 类型。



## 配置

```properties
com.gaoice.easyexcel.enable-excel-file=true # 是否解析 ExcelFile 类型的返回值为文件下载
com.gaoice.easyexcel.enable-sheet-info=true # 是否解析 SheetInfo 类型的返回值为文件下载
com.gaoice.easyexcel.enable-response-excel=true # 是否解析带有 @ResponseExcel 注解的函数的返回值为文件下载
```



## 示例

查看 [ExcelDownloadController](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/web/ExcelDownloadController.java) 中的使用示例。

运行 [DemoApplication](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/DemoApplication.java) 访问相应的接口查看效果。

