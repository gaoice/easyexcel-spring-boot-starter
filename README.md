# Easyexcel Spring Boot Starter
## 概述

在 Spring Boot 中使用 `@RequestExcel` 和 `@ResponseExcel` 注解轻松上传和下载 Excel 文件。



## 使用

下载示例，类似于 `@ResponseBody` ：

```java
@RequestMapping("/download")
@ResponseExcel({"name", "book.name", "book.author"})
public List<Character> download() {
    return listCharacters();
}
```

上传示例，类似于 `@RequestParam` ：

```java
@RequestMapping("/upload")
@ResponseBody
public List<Character> upload(@RequestExcel(value = "excel", targetClass = Character.class, 
                                    		fieldNames = {"name", "book.name", "book.author"}) 
                      		  List<Character> characters) {
    return characters;
}
```



已提交至 `Maven` 中央仓库。

`Maven`

```xml
<dependency>
    <groupId>com.gaoice</groupId>
    <artifactId>easyexcel-spring-boot-starter</artifactId>
    <version>2.1</version>
</dependency>
```



## 新版本

- v 2.1，提升 `@ResponseExcel` 注解优先级，现在可以在 `@RestController` 中使用 `@ResponseExcel` 注解。
  
- v 2.0，新增 `@RequestExcel` 注解。



## 示例

查看 [ExcelUploadController](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/web/ExcelUploadController.java) 中的上传示例。

查看 [ExcelDownloadController](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/web/ExcelDownloadController.java) 中的下载示例。

运行 [DemoApplication](https://github.com/gaoice/easyexcel-spring-boot-starter/tree/master/src/test/java/com/gaoice/easyexcel/spring/boot/demo/DemoApplication.java) ，在 [upload.html](https://github.com/gaoice/easyexcel-spring-boot-starter/blob/master/src/test/resources/templates/upload.html) 尝试导入功能，访问相应的接口查看效果。

