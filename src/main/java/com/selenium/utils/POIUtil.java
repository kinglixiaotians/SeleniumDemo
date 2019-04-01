package com.selenium.utils;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 李啸天 on 2018/10/9.
 */
@Slf4j
public class POIUtil {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";


    /**
     * 读取文件，并修改excel文件中的优分金额
     * @param file
     */
    public static void readExcel(File file) {
        try {
            FileInputStream in = new FileInputStream(file); // 文件流
            //XSSFWorkbook 适用于xlsx
            //Workbook workbook = new XSSFWorkbook(in);
            //HSSFWorkbook 适用于xls
            Workbook workbook2 = new HSSFWorkbook(in);
            //获取第一个sheet
            Sheet sheet = workbook2.getSheetAt(0);
            //表格总计多少行
            int total = sheet.getLastRowNum();
            //遍历每一行
            for(Row row : sheet){
                //获取第1，2个单元格对象
                //如果cell为空，直接用row.createCell(0)就行
                Cell cell1 = row.getCell(2);
                cell1.setCellType(CellType.STRING);
                String cellValue = cell1.getStringCellValue();
                System.out.println(cellValue);
                if("0".equals(cellValue)){
                    cell1.removeCellComment();
                    cell1.setCellValue("100");
                }
            }
            //对文档的写入还在缓存区，需要把流刷到文件中
            FileOutputStream out=new FileOutputStream(file);
            workbook2.write(out);
            //该关闭的关闭
            workbook2.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        File file = new File("E:\\2019\\20190401\\20190401154424570182.xls");
        POIUtil.readExcel(file);
    }
}
