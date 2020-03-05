package com.gaoice.easyexcel.spring.boot.demo.web;

import com.gaoice.easyexcel.SheetInfo;
import com.gaoice.easyexcel.spring.boot.autoconfigure.ExcelFile;
import com.gaoice.easyexcel.spring.boot.autoconfigure.annotation.ResponseExcel;
import com.gaoice.easyexcel.spring.boot.demo.model.Book;
import com.gaoice.easyexcel.spring.boot.demo.model.Character;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 Controller 上使用 @RestController 或者 @ResponseBody 会导致 starter 失效
 */
@Controller
@RequestMapping("/test")
public class ExcelDownloadController {

    /**
     * 文件名默认使用 sheetName
     * 生效条件：
     * enable-sheet-info=true
     * 返回值类型为 SheetInfo
     * 不使用 @ResponseBody 注解
     *
     * @return SheetInfo
     */
    @RequestMapping("/sheetInfo")
    public SheetInfo sheetInfo() {
        String sheetName = "人物角色列表";
        String[] columnNames = {"角色名", "书名", "作者"};
        String[] classFieldNames = {"name", "book.name", "book.author"};
        List data = listCharacters();
        return new SheetInfo(sheetName, columnNames, classFieldNames, data);
    }

    /**
     * 生效条件：
     * enable-excel-file=true
     * 返回值类型为 ExcelFile
     * 不使用 @ResponseBody 注解
     *
     * @return ExcelFile
     */
    @RequestMapping("/excelFile")
    public ExcelFile excelFile() {
        /* SheetInfo */
        String sheetName = "人物角色列表";
        String[] columnNames = {"角色名", "书名", "作者"};
        String[] classFieldNames = {"name", "book.name", "book.author"};
        List data = listCharacters();
        SheetInfo sheetInfo1 = new SheetInfo(sheetName, columnNames, classFieldNames, data);

        String sheetName2 = "第二个sheet";
        SheetInfo sheetInfo2 = new SheetInfo(sheetName2, columnNames, classFieldNames, data);

        /* 通过 ExcelFile 自定义下载的文件名，放入多个 sheet */
        ExcelFile excelFile = new ExcelFile();
        excelFile.setFileName("ExcelFile可以自定义文件名和放入多个sheet");
        excelFile.putSheet(sheetInfo1);
        excelFile.putSheet(sheetInfo2);

        return excelFile;
    }

    /**
     * 注解 @ResponseExcel 的必须值：classFieldNames
     * sheetName 默认值为 default
     * fileName 默认使用 sheetName 的值
     * columnNames 默认使用 classFieldNames 的值
     * sheetStyle 可以指定样式，默认为 DefaultSheetStyle.class
     * 生效条件：
     * enable-response-excel=true
     * 使用 @ResponseExcel 注解
     * 不使用 @ResponseBody 注解
     *
     * @return List
     */
    @RequestMapping("/list")
    @ResponseExcel(classFieldNames = {"name", "book.name", "book.author"})
    public List list() {
        return listCharacters();
    }

    /**
     * 生成测试数据
     *
     * @return List
     */
    private List listCharacters() {
        Book b1 = new Book();
        b1.setName("多情剑客无情剑");
        b1.setAuthor("古龙");
        Character c1 = new Character();
        c1.setName("李寻欢");
        c1.setBook(b1);
        Character c2 = new Character();
        c2.setName("林诗音");
        c2.setBook(b1);
        Character c3 = new Character();
        c3.setName("林仙儿");
        c3.setBook(b1);

        Book b2 = new Book();
        b2.setName("动物庄园");
        b2.setAuthor("乔治·奥威尔");
        Character c4 = new Character();
        c4.setName("雪球");
        c4.setBook(b2);
        Character c5 = new Character();
        c5.setName("拳师");
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
