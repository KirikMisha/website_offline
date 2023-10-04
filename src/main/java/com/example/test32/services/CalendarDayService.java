package com.example.test32.services;

import com.example.test32.models.CalendarDay;
import com.example.test32.repository.CalendarDayRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarDayService {

    private final String CALENDAR_URL = "https://www.calend.ru/holidays/october/";

    @Autowired
    private CalendarDayRepository calendarDayRepository;

    public void scrapeCalendarDays() {
        try {
            // Get the document from the calendar URL
            Document document = Jsoup.connect(CALENDAR_URL).get();

            // Find all the calendar day elements
            Elements dayElements = document.select("li.full");
            for (Element dayElement : dayElements) {
                // Get the date and format it
                String dataNum = dayElement.select(".dataNum a").attr("href").split("/")[2];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
                LocalDate date = LocalDate.parse(dataNum, formatter);


                // Get the additional information columns
                Elements captionElements = dayElement.select(".caption .title");
                List<String> additionalInfoList = new ArrayList<>();
                for (Element captionElement : captionElements) {
                    additionalInfoList.add(captionElement.text());
                }

                // Get the links to international information
                Elements linkElements = dayElement.select(".caption .title > a[href]");
                List<String> internationalInformationList = new ArrayList<>();
                for (Element linkElement : linkElements) {
                    String link = linkElement.attr("href");
                    List<String> internationalInformation = scrapeInternationalInformation(link);
                    internationalInformationList.addAll(internationalInformation);
                }

                // Check if a CalendarDay object already exists for the given day and month
                int day = date.getDayOfMonth();
                int month = date.getMonthValue();
                CalendarDay existingCalendarDay = calendarDayRepository.findByDayAndMonth(day, month);

                if (existingCalendarDay != null) {
                    // If the object exists, check each column for differences
                    boolean shouldUpdate = false;

                    // Check the other columns for differences and update if necessary
                    // ... Add checks for the other columns

                    // If any column has been updated, save the changes
                    if (shouldUpdate) {
                        calendarDayRepository.save(existingCalendarDay);
                    }
                } else {
                    // If the object doesn't exist, create a new one and save it
                    CalendarDay newCalendarDay = new CalendarDay();
                    newCalendarDay.setDay(day);
                    newCalendarDay.setMonth(month);
                    newCalendarDay.setDate(date);
//                    newCalendarDay.setDescription(description);

                    // Set additional information columns
                    for (int i = 0; i < additionalInfoList.size() && i < 10; i++) {
                        String additionalInfoColumn = "additionalInfo" + (i + 1);
                        setAdditionalInfoColumn(newCalendarDay, additionalInfoColumn, additionalInfoList.get(i));
                    }

                    // Set international information columns
                    for (int i = 0; i < internationalInformationList.size() && i < 9; i++) {
                        String internationalInfoColumn = "internationalInformation" + (i + 1);
                        setInternationalInfoColumn(newCalendarDay, internationalInfoColumn, internationalInformationList.get(i));
                    }

                    calendarDayRepository.save(newCalendarDay);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAdditionalInfoColumn(CalendarDay calendarDay, String column, String value) {
        try {
            calendarDay.getClass().getMethod("set" + capitalize(column), String.class).invoke(calendarDay, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInternationalInfoColumn(CalendarDay calendarDay, String column, String value) {
        try {
            calendarDay.getClass().getMethod("set" + capitalize(column), String.class).invoke(calendarDay, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private List<String> scrapeInternationalInformation(String link) {
        List<String> internationalInformationList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(link).get();
            Elements internationalTextElements = document.select("div.maintext p");
            if (!internationalTextElements.isEmpty()) {
                StringBuilder internationalInfoBuilder = new StringBuilder();
                for (Element element : internationalTextElements) {
                    internationalInfoBuilder.append(element.text()).append("\n");
                }
                internationalInformationList.add(internationalInfoBuilder.toString().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return internationalInformationList;
    }

    public CalendarDay getCalendarDayByDate(LocalDate date) {
        return calendarDayRepository.findByDate(date);
    }

    public List<CalendarDay> getAllCalendarDays() {
        return calendarDayRepository.findAll();
    }

    public CalendarDay getCalendarDayById(Long id) {
        return calendarDayRepository.findById(id).orElse(null);
    }

}
