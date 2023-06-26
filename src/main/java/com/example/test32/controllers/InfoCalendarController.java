package com.example.test32.controllers;

import com.example.test32.models.CalendarDay;
import com.example.test32.services.CalendarDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/details")
public class InfoCalendarController {
    @Autowired
    private CalendarDayService calendarDayService;

    @GetMapping("/info/{infoNumber}")
    public String showInfo(@PathVariable("infoNumber") int infoNumber, @RequestParam("id") Long id, Model model) {
        CalendarDay calendarDay = calendarDayService.getCalendarDayById(id);
        if (calendarDay != null) {
            String internationalInformation = getInternationalInformation(calendarDay, infoNumber);
            model.addAttribute("internationalInformation", internationalInformation);
            model.addAttribute("id", id);
            model.addAttribute("additionalInfoList", calendarDay.getAdditionalInfoList());
        }
        return "calendar-day-info";
    }

    private String getInternationalInformation(CalendarDay calendarDay, int infoNumber) {
        String additionalInfo = "";
        try {
            additionalInfo = (String) calendarDay.getClass().getMethod("getInternationalInformation" + infoNumber).invoke(calendarDay);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return additionalInfo;
    }
}
