package com.example.test32.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ExcelToDatabaseImporter {
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\Михаил\\Desktop\\list_people.xlsx";

        try (FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spring_work_web", "root", "root");
            String insertQuery = "INSERT INTO person (phone_number, landline_phone, last_name, first_name, middle_name, office_number, position) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            Sheet sheet = workbook.getSheetAt(0); // Предполагается, что данные находятся в первом листе.

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Пропустить первую строку с заголовками столбцов.
                    continue;
                }

                String phoneNumber = getCellStringValue(row.getCell(0));
                String landlinePhone = getCellStringValue(row.getCell(1));
                String fullName = getCellStringValue(row.getCell(2));
                String[] nameParts = fullName.split(" ");
                String lastName = (nameParts.length > 0) ? nameParts[0] : "";
                String firstName = (nameParts.length > 1) ? nameParts[1] : "";
                String middleName = (nameParts.length > 2) ? nameParts[2] : "";
                String officeNumber = getCellStringValue(row.getCell(3));
                String position = getCellStringValue(row.getCell(4));

                preparedStatement.setString(1, phoneNumber);
                preparedStatement.setString(2, landlinePhone);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, firstName);
                preparedStatement.setString(5, middleName);
                preparedStatement.setString(6, officeNumber);
                preparedStatement.setString(7, position);

                preparedStatement.executeUpdate();
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            // Преобразуйте числовое значение в текст.
            return String.valueOf((int)cell.getNumericCellValue());
        } else {
            return "";
        }
    }
}
