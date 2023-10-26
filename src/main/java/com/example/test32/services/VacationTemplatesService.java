package com.example.test32.services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class VacationTemplatesService {

    public byte[] generatePdfDocument(String firstName, String lastName, String surname, LocalDate startDate, LocalDate endDate, String profession, String departmentNumber) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            ClassPathResource resource = new ClassPathResource("static/fonts/times.ttf");
            PDFont font = PDType0Font.load(document, resource.getInputStream());

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float margin = 56.7f; // Отступ сверху и снизу 2 см
            float marginLeft = 85.0f; // Отступ слева 2.5 см
            float marginRight = 56.7f; // Отступ справа 2 см
            float marginTop = 56.7f; // Отступ сверху 2 см
            float marginBottom = 56.7f; // Отступ снизу 2 см
            contentStream.setLineWidth(0.5f);

            contentStream.setFont(font, 14);

            long daysInVacation = startDate.until(endDate, ChronoUnit.DAYS);

            String[] rightAlignedLines = {
                    "Генеральному директору",
                    "АО «НПП « Краснознамёнец»",
                    "Варенице В.И.",
                    "от " + profession,
                    "цехa №" + departmentNumber,
                    lastName + " " + firstName + " " + surname
            };

            String centerAlignedLine = "Заявление";

            String[] centerAlignedLines = {
                    "Прошу предоставить мне ежегодный оплачиваемый отпуск по графику в",
                    "количестве " + daysInVacation + " дней, с " + startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))+"."
            };
            String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            String[] leftAlignedLine = {
                    todayDate,
                    "________________"

            };

            String[] rightAlignedLine = {
                    "СОГЛАСОВАНО ",
                    "Начальник отдела ",
                    "Директор по направлению"
        };

            float yPosition = PDRectangle.A4.getHeight() - margin - 14; // Начальная позиция верхнего текста

            for (String line : rightAlignedLines) {
                float textWidth = font.getStringWidth(line) * 14 / 1000; // Получение ширины текста
                float xPosition = PDRectangle.A4.getWidth() - margin - marginRight - textWidth;

                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(line);
                contentStream.endText();
                yPosition -= 20; // Смещаем позицию для следующей строки
            }

            // Вычисляем позицию x для центрального выравнивания
            float textWidth = font.getStringWidth(centerAlignedLine) * 14 / 1000;
            float xPosition = (PDRectangle.A4.getWidth() - textWidth) / 2;
            yPosition -= 40;

            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(centerAlignedLine);
            contentStream.endText();
            yPosition -= 40; // Увеличьте отступ между заголовком и следующей строкой

            for (String line : centerAlignedLines) {
                textWidth = font.getStringWidth(line) * 14 / 1000; // Получение ширины текста
                xPosition = marginLeft; // Левый отступ

                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(line);
                contentStream.endText();
                yPosition -= 20; // Смещаем позицию для следующей строки
            }
            for (String line : leftAlignedLine){
                textWidth = font.getStringWidth(line) * 14 / 1000; // Получение ширины текста
                xPosition = PDRectangle.A4.getWidth() - margin - marginRight - textWidth;

                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(line);
                contentStream.endText();
                yPosition -= 20; // Смещаем позицию для следующей строки
            }
            yPosition -= 120;
            for (String line : rightAlignedLine) {
                textWidth = font.getStringWidth(line) * 14 / 1000; // Получение ширины текста
                xPosition = marginLeft; // Левый отступ

                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(line);
                contentStream.endText();
                yPosition -= 20; // Смещаем позицию для следующей строки
            }

            xPosition = marginLeft;

            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.endText();
            yPosition -= 20;

            xPosition = PDRectangle.A4.getWidth() - margin - marginRight - textWidth;

            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.endText();

            contentStream.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.save(baos);
            document.close();

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
