package com.gaoice.easyexcel.spring.boot.autoconfigure;

import com.gaoice.easyexcel.writer.SheetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaoice
 */
public class ExcelFile {

    private String fileSuffix = ".xlsx";
    private String fileName;
    private List<SheetInfo> sheetInfoList;

    public void addSheet(SheetInfo sheetInfo) {
        if (sheetInfoList == null) {
            sheetInfoList = new ArrayList<>();
        }
        sheetInfoList.add(sheetInfo);
    }

    /**
     * rename function
     * replaced by <code>ExcelFile.addSheet(SheetInfo sheetInfo)</code>
     */
    @Deprecated
    public void putSheet(SheetInfo sheetInfo) {
        addSheet(sheetInfo);
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<SheetInfo> getSheetInfoList() {
        return sheetInfoList;
    }

    public void setSheetInfoList(List<SheetInfo> sheetInfoList) {
        this.sheetInfoList = sheetInfoList;
    }
}
