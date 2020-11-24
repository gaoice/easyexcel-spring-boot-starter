# Easyexcel Spring Boot Starter
## 概述

在 Controller 中使用 `@RequestExcel` 和 `@ResponseExcel` 注解轻松上传/下载 Excel 文件。



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

- v 2.0，新增 `@RequestExcel` 注解。



## 配置

```properties
com.gaoice.easyexcel.enable-excel-file=true # 是否解析 ExcelFile 类型的返回值为文件下载
com.gaoice.easyexcel.enable-sheet-info=true # 是否解析 SheetInfo 类型的返回值为文件下载
com.gaoice.easyexcel.enable-response-excel=true # 是否解析带有 @ResponseExcel 注解的函数的返回值为文件下载
```



## 示例

查看 [ExcelUploadController](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/web/ExcelUploadController.java) 中的上传示例。

查看 [ExcelDownloadController](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/web/ExcelDownloadController.java) 中的下载示例。

运行 [DemoApplication](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/DemoApplication.java) ，在 [upload.html](https://github.com/gaoice/easyexcel-spring-boot-starter/blob/master/src/test/resources/templates/upload.html) 尝试导入功能，访问相应的接口查看效果。

