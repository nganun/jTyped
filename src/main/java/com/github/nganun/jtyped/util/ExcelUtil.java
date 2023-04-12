package com.github.nganun.jtyped.util;

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

    private static String DICT_PATH = System.getProperty("user.home") + "/.dot/en.xlsx";
    private static File DICT_FILE = new File(DICT_PATH);

    public static void addWord(String[] dictValues) {
        if (!DICT_FILE.exists()) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("jTyped");
            Row row = sheet.createRow(0);

            String[] dictLabels = {"word", "phonetic", "tense", "description"};

            int cellnum = 0;
            for (Object obj : dictLabels) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) cell.setCellValue((String) obj);
                else if (obj instanceof Integer) cell.setCellValue((Integer) obj);
            }

            try (FileOutputStream out = new FileOutputStream(DICT_FILE)){
                workbook.write(out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            appendRow(dictValues, DICT_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void appendRow(String[] dict, File file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        Sheet sheet = workbook.getSheetAt(0);

        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum++);

        int cellNum = 0;
        for (Object obj : dict) {
            Cell cell = row.createCell(cellNum++);
            if (obj instanceof String)
                cell.setCellValue((String) obj);
            else if (obj instanceof Integer)
                cell.setCellValue((Integer) obj);
        }

        try (FileOutputStream out = new FileOutputStream(file);) {
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isExist(String word) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(DICT_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getPhysicalNumberOfRows();

        for (int i = 0; i < rowCount; i++) {
            String currentWord = String.valueOf(sheet.getRow(i).getCell(0));
            if (currentWord.toLowerCase().equals(word.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        String[] hello = {"game", "gei mu", "游戏", "n 名词"};
        addWord(hello);
    }
}
