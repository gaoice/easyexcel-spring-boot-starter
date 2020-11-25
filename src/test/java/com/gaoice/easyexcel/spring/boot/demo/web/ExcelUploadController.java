package com.gaoice.easyexcel.spring.boot.demo.web;

import com.gaoice.easyexcel.reader.converter.CellConverter;
import com.gaoice.easyexcel.reader.sheet.SheetParserContext;
import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.RequestExcel;
import com.gaoice.easyexcel.spring.boot.demo.model.Character;
import com.gaoice.easyexcel.spring.boot.demo.model.GenderEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author gaoice
 */
@Controller
@RequestMapping("/test")
public class ExcelUploadController {

    @GetMapping("/upload")
    public String uploadView() {
        return "/upload";
    }

    /**
     * 解析为 mapList ，最简单的情况只需要 @RequestExcel("excel") List<Map<?, ?>> mapList 作为入参
     * 类似于使用 @RequestParam("excel") MultipartFile file 读取上传的文件
     */
    @PostMapping("/upload1")
    @ResponseBody
    public List<?> upload1(@RequestExcel("excel") List<Map<?, ?>> mapList) {
        return mapList;
    }

    /**
     * 指定目标类和字段名
     */
    @PostMapping("/upload2")
    @ResponseBody
    public List<?> upload2(@RequestExcel(value = "excel",
            fieldNames = {"name", "book.name", "book.author"},
            targetClass = Character.class) List<Character> characters) {
        return characters;
    }

    /**
     * 使用 CellConverter
     */
    @PostMapping("/upload3")
    @ResponseBody
    public List<Character> upload3(@RequestExcel(value = "excel",
            targetClass = Character.class,
            fieldNames = {"name", "gender", "book.name", "book.author"},
            map = {@RequestExcel.Node(key = "gender", value = GenderCellConverter.class)}) List<Character> characters) {
        return characters;
    }

    public static class GenderCellConverter implements CellConverter {
        @Override
        public Object convert(SheetParserContext context) {
            String v = context.getStringValue();
            if (v == null) {
                return GenderEnum.UNKNOWN;
            }
            if ("男性角色".equals(v)) {
                return GenderEnum.MALE;
            }
            if ("女性角色".equals(v)) {
                return GenderEnum.FEMALE;
            }
            return GenderEnum.UNKNOWN;
        }
    }
}
