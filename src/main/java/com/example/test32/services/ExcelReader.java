package com.example.test32.services;//package com.example.test30.services;
//
//import com.example.test30.models.PersonsEntity;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ExcelReader {
//
//    public List<PersonsEntity> readExcelFile(String filePath) throws IOException {
//        List<PersonsEntity> persons = new ArrayList<>();
//
//        try (FileInputStream file = new FileInputStream(filePath);
//             Workbook workbook = new XSSFWorkbook(file)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//
//            int rowNum = 27; // Начальная строка
//            while (rowNum <= sheet.getLastRowNum()) {
//                Row positionRow = sheet.getRow(rowNum);
//                Row personRow = sheet.getRow(rowNum + 1);
//
//                if (positionRow != null && personRow != null) {
//                    String position = getCellValueAsString(positionRow.getCell(1));
//
//                    Cell nextCell = positionRow.getCell(5);
//                    if (nextCell == null || nextCell.getCellType() == CellType.BLANK) {
//                        String fullNameCell = getCellValueAsString(personRow.getCell(1));
//                        String[] fullName = fullNameCell.split(" ");
//
//                        if (fullName.length == 3) {
//                            String lastName = fullName[0];
//                            String firstName = fullName[1];
//                            String middleName = fullName[2];
//
//                            String phoneNumber = getCellValueAsString(personRow.getCell(3));
//                            String officeNumber = getCellValueAsString(personRow.getCell(4));
//
//                            PersonsEntity person = new PersonsEntity();
//                            person.setFirstName(firstName);
//                            person.setLastName(lastName);
//                            person.setMiddleName(middleName);
//                            person.setPosition(position);
//                            person.setOfficeNumber(officeNumber);
//                            person.setPhoneNumber(phoneNumber);
//
//                            persons.add(person);
//                        }
//                    }
//                }
//
//                rowNum += 2; // Переход к следующей паре строк
//            }
//        }
//
//        return persons;
//    }
//
//    private String getCellValueAsString(Cell cell) {
//        if (cell == null) {
//            return "";
//        }
//
//        if (cell.getCellType() == CellType.STRING) {
//            return cell.getStringCellValue();
//        } else if (cell.getCellType() == CellType.NUMERIC) {
//            double numericValue = cell.getNumericCellValue();
//            return String.valueOf(numericValue);
//        } else {
//            return "";
//        }
//    }
//}
