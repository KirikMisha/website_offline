package com.example.test32.services;

import com.example.test32.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParserService {
    private final CalendarDayService calendarDayService;
//    private final ExcelReader excelReader;
    private final PersonsRepository personRepository;

    @Autowired
//    ExcelReader excelReader,
    public ParserService(CalendarDayService calendarDayService, PersonsRepository personRepository) {
        this.calendarDayService = calendarDayService;
//        this.excelReader = excelReader;
        this.personRepository = personRepository;
    }
}
