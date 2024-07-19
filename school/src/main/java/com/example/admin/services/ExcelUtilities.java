package com.example.admin.services;

import com.example.admin.models.Registered;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtilities {
    String filePath = System.getProperty("user.dir") + "/src/main/resources/registered_students.xlsx";

    public void writeToExcel(List<Registered> registeredList) throws InvalidFormatException {
        File file = new File(filePath);
        boolean fileExists = file.exists();
        int lastRowNum = 0;

        if (fileExists) {
            try (Workbook existingWorkbook = WorkbookFactory.create(file)) {
                Sheet existingSheet = existingWorkbook.getSheet("Registered Students");
                lastRowNum = existingSheet.getLastRowNum() + 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Set the starting row number for new data
        int rowNum = lastRowNum > 0 ? lastRowNum : 1;
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Registered Students");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("User Name");
            headerRow.createCell(1).setCellValue("First Name");
            headerRow.createCell(2).setCellValue("Last Name");
            headerRow.createCell(3).setCellValue("School ID");
            headerRow.createCell(4).setCellValue("Profile Picture");
            headerRow.createCell(5).setCellValue("Email Address");
            headerRow.createCell(6).setCellValue("Date of Birth");
            headerRow.createCell(7).setCellValue("Password");

            // Populate data rows
            for (Registered registered : registeredList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum);
                row.createCell(1).setCellValue(registered.getUserName());
                row.createCell(2).setCellValue(registered.getFirstName());
                row.createCell(3).setCellValue(registered.getLastName());
                row.createCell(4).setCellValue(registered.getSchoolId());
                row.createCell(5).setCellValue(registered.getProfilePicture());
                row.createCell(6).setCellValue(registered.getEmailAddress());
                row.createCell(7).setCellValue(registered.getDateOfBirth());
                row.createCell(8).setCellValue(registered.getPassword());
            }

            // Write the workbook to the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public Registered searchByUsername(String username) throws InvalidFormatException {
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
