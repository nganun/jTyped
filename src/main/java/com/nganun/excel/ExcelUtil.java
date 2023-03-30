package com.nganun.excel;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    private static Map<Integer, Object[]> prepareData(int rowNum, List<DictEntity> recordsToWrite) {
        Map<Integer, Object[]> data = new HashMap<>();
        for (DictEntity entity : recordsToWrite) {
            rowNum++;
            data.put(rowNum, new Object[]{rowNum, entity.getWord(), entity.getPhonetic(), entity.getTense(), entity.getDescription()});
        }
        return data;
    }

    public static void addWord() {
        File file = new File("C:\\temp\\data.xlsx");
        if (!file.exists()) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("Employee Data");
            //This data needs to be written (Object[])
            Map<String, Object[]> data = new TreeMap<String, Object[]>();
            data.put("1", new Object[]{"word", "phonetic", "tense", "description"});

            //Iterate over data and write to sheet
            Set<String> keyset = data.keySet();
            int rownum = 0;
            for (String key : keyset) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(key);
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) cell.setCellValue((String) obj);
                    else if (obj instanceof Integer) cell.setCellValue((Integer) obj);
                }
            }
            try {
                //Write the workbook in file system
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        List<DictEntity> recordsToWrite = List.of(
                new DictEntity("hello 3", "he lou", "动词","你好"),
                new DictEntity("world 3", "wo de", "动词","世界")
        );
        try {
            appendRows(recordsToWrite, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendRows(List<DictEntity> recordsToWrite, File file)
            throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);
        int rowNum = sheet.getLastRowNum() + 1;
        Map<Integer, Object[]> data = prepareData(rowNum, recordsToWrite);
        Set<Integer> keySet = data.keySet();
        for (Integer key : keySet) {
            Row row = sheet.createRow(rowNum++);
            Object[] objArr = data.get(key);
            int cellNum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellNum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        addWord();
    }
}
