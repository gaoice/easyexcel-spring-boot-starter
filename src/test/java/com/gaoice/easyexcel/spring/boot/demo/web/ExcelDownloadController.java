package com.gaoice.easyexcel.spring.boot.demo.web;

import com.gaoice.easyexcel.spring.boot.autoconfigure.ExcelFile;
import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.ResponseExcel;
import com.gaoice.easyexcel.spring.boot.demo.model.Book;
import com.gaoice.easyexcel.spring.boot.demo.model.Character;
import com.gaoice.easyexcel.spring.boot.demo.model.GenderEnum;
import com.gaoice.easyexcel.writer.SheetInfo;
import com.gaoice.easyexcel.writer.handler.FieldValueConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2.1 ，在 high-priority=true 的情况（即默认配置）下，
 * 对 SheetInfo、ExcelFile、 @ResponseExcel 处理的优先级高于 Spring MVC 的 @RestController @ResponseBody 等相关注解
 */
@RestController
@RequestMapping("/test")
public class ExcelDownloadController {

    /**
     * 文件名默认使用 sheetName
     *
     * @return SheetInfo
     */
    @ResponseBody
    @RequestMapping("/sheetInfo")
    public SheetInfo sheetInfo() {
        String sheetName = "人物角色列表";
        String[] columnNames = {"角色名", "书名", "作者"};
        String[] classFieldNames = {"name", "book.name", "book.author"};
        List<Character> data = listCharacters();
        return new SheetInfo(sheetName, columnNames, classFieldNames, data);
    }

    /**
     * @return ExcelFile
     */
    @RequestMapping("/excelFile")
    public ExcelFile excelFile() {
        /* SheetInfo */
        String sheetName = "人物角色列表";
        String[] columnNames = {"角色名", "书名", "作者"};
        String[] classFieldNames = {"name", "book.name", "book.author"};
        List<Character> data = listCharacters();
        SheetInfo sheetInfo1 = new SheetInfo(sheetName, columnNames, classFieldNames, data);

        String sheetName2 = "第二个sheet";
        SheetInfo sheetInfo2 = new SheetInfo(sheetName2, columnNames, classFieldNames, data);

        /* 通过 ExcelFile 自定义下载的文件名，放入多个 sheet */
        ExcelFile excelFile = new ExcelFile();
        excelFile.setFileName("ExcelFile可以自定义文件名和放入多个sheet");
        excelFile.addSheet(sheetInfo1);
        excelFile.addSheet(sheetInfo2);

        return excelFile;
    }

    /**
     * 注解 @ResponseExcel 的必须值：classFieldNames
     * sheetName 默认值为 default
     * fileName 默认使用 sheetName 的值
     * columnNames 默认使用 classFieldNames 的值
     * sheetStyle 可以指定样式，默认为 DefaultSheetStyle.class
     *
     * @return List
     */
    @RequestMapping("/list")
    @ResponseExcel({"name", "book.name", "book.author"})
    public List<Character> list() {
        return listCharacters();
    }

    /**
     * 在注解中为指定字段设置处理器
     */
    @RequestMapping("/template")
    @ResponseExcel(value = {"name", "gender", "book.name", "book.author"},
            columnNames = {"角色姓名", "角色性别", "书名", "作者"},
            map = {@ResponseExcel.Node(key = "gender", value = GenderConverter.class)},
            fileName = "上传模板")
    public List<Character> template() {
        return listCharacters();
    }

    public static class GenderConverter implements FieldValueConverter<GenderEnum> {
        @Override
        public Object convert(GenderEnum value) {
            return value == null ? null : value.equals(GenderEnum.MALE) ? "男性角色" : value.equals(GenderEnum.FEMALE) ? "女性角色" : "其他角色";
        }
    }

    /**
     * 生成测试数据
     *
     * @return List
     */
    private List<Character> listCharacters() {
        Book b1 = new Book();
        b1.setName("多情剑客无情剑");
        b1.setAuthor("古龙");
        Character c1 = new Character();
        c1.setName("李寻欢");
        c1.setGender(GenderEnum.MALE);
        c1.setBook(b1);
        Character c2 = new Character();
        c2.setName("林诗音");
        c2.setGender(GenderEnum.FEMALE);
        c2.setBook(b1);
        Character c3 = new Character();
        c3.setName("林仙儿");
        c3.setGender(GenderEnum.FEMALE);
        c3.setBook(b1);

        Book b2 = new Book();
        b2.setName("动物庄园");
        b2.setAuthor("乔治·奥威尔");
        Character c4 = new Character();
        c4.setName("雪球");
        c4.setGender(GenderEnum.UNKNOWN);
        c4.setBook(b2);
        Character c5 = new Character();
        c5.setName("拳师");
        c5.setGender(GenderEnum.UNKNOWN);
        c5.setBook(b2);

        List<Character> list = new ArrayList<Character>();
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        return list;
    }
}
