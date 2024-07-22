package com.example.admin.services;

import com.example.admin.models.Registered;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ExcelUtilities {
    String filePath = "registered_students.xlsx";

    public void writeToExcel(Registered registered) throws EncryptedDocumentException, IOException {
        // Blank workbook
        while (filePath == null) {
            try (XSSFWorkbook workbook = new XSSFWorkbook()) {
                Sheet sheet = workbook.createSheet("Registered Students");
                System.out.println("New File created successfully.." + sheet.getSheetName());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet("Registered Students");

        int lastRowNum = sheet.getLastRowNum();
        System.out.println("Last row number: " + lastRowNum);
        Row row = sheet.createRow(++lastRowNum);

        row.createCell(0).setCellValue(registered.getUserName());
        row.createCell(1).setCellValue(registered.getFirstName());
        row.createCell(2).setCellValue(registered.getLastName());
        row.createCell(3).setCellValue(registered.getSchoolId());
        row.createCell(4).setCellValue(registered.getProfilePicture());
        row.createCell(5).setCellValue(registered.getEmailAddress());
        row.createCell(6).setCellValue(registered.getDateOfBirth());
        row.createCell(7).setCellValue(registered.getPassword());

        try {
            FileOutputStream fileOut = new FileOutputStream(new File(filePath));
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Excel file written successfully..");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

    }

    public List<Registered> readFromExcel(Integer schoolId) throws InvalidFormatException {
        List<Registered> registeredList = new ArrayList<>();
        File file = new File(filePath);
        boolean fileExists = file.exists();

        if (fileExists) {
            try (Workbook workbook = WorkbookFactory.create(file)) {
                Sheet sheet = workbook.getSheet("Registered Students");
                int lastRowNum = sheet.getLastRowNum();

                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    int rowSchoolId = (int) row.getCell(4).getNumericCellValue();
                    if (rowSchoolId == schoolId) {
                        Registered registered = new Registered();
                        registered.setUserName(row.getCell(1).getStringCellValue());
                        registered.setFirstName(row.getCell(2).getStringCellValue());
                        registered.setLastName(row.getCell(3).getStringCellValue());
                        registered.setSchoolId((int) row.getCell(4).getNumericCellValue());
                        registered.setProfilePicture(row.getCell(5).getStringCellValue());
                        registered.setEmailAddress(row.getCell(6).getStringCellValue());
                        registered.setDateOfBirth(row.getCell(7).getDateCellValue());
                        registered.setPassword(row.getCell(8).getStringCellValue());
                        registeredList.add(registered);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return registeredList;
    }

    public void test() {
        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");

        // Prepare data to be written as an Object[]
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] { "ID", "NAME", "LASTNAME" });
        data.put("2", new Object[] { 1, "Amit", "Shukla" });
        data.put("3", new Object[] { 2, "Lokesh", "Gupta" });
        data.put("4", new Object[] { 3, "John", "Adwards" });
        data.put("5", new Object[] { 4, "Brian", "Schultz" });

        // Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {

            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String)
                    cell.setCellValue((String) obj);
                else if (obj instanceof Integer)
                    cell.setCellValue((Integer) obj);
            }
        }

        // Write the workbook in file system
        try {
            FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Registered searchByUsername(String username) {
        File file = new File(filePath);
        boolean fileExists = file.exists();

        if (fileExists) {
            try (Workbook workbook = WorkbookFactory.create(file)) {
                Sheet sheet = workbook.getSheet("Registered Students");
                int lastRowNum = sheet.getLastRowNum();

                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    String rowUsername = row.getCell(1).getStringCellValue();
                    if (rowUsername.equals(username)) {
                        Registered registered = new Registered();
                        registered.setUserName(rowUsername);
                        registered.setFirstName(row.getCell(2).getStringCellValue());
                        registered.setLastName(row.getCell(3).getStringCellValue());
                        registered.setSchoolId((int) row.getCell(4).getNumericCellValue());
                        registered.setProfilePicture(row.getCell(5).getStringCellValue());
                        registered.setEmailAddress(row.getCell(6).getStringCellValue());
                        registered.setDateOfBirth(row.getCell(7).getDateCellValue());
                        registered.setPassword(row.getCell(8).getStringCellValue());
                        return registered;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void deleteByUsername(String username) throws InvalidFormatException {
        File file = new File(filePath);
        boolean fileExists = file.exists();

        if (fileExists) {
            try (Workbook workbook = WorkbookFactory.create(file)) {
                Sheet sheet = workbook.getSheet("Registered Students");
                int lastRowNum = sheet.getLastRowNum();

                for (int i = 1; i <= lastRowNum; i++) {
                    Row row = sheet.getRow(i);
                    String rowUsername = row.getCell(1).getStringCellValue();
                    if (rowUsername.equals(username)) {
                        sheet.removeRow(row);
                        break;
                    }
                }

                // Shift rows up to remove the deleted row
                sheet.shiftRows(1, lastRowNum, -1);

                // Write the updated workbook to the file
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
