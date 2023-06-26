package com.example.test32.config;//package com.example.test30.config;
//
//import com.example.test30.models.CalendarDay;
//
//public class FormattingUtils {
//    public static String formatAdditionalInfo(CalendarDay calendarDay) {
//        if (calendarDay == null) {
//            return "";
//        }
//
//        String additionalInfo = calendarDay.getAdditionalInfo();
//        if (additionalInfo == null || additionalInfo.isEmpty()) {
//            return "";
//        }
//
//        StringBuilder formattedInfo = new StringBuilder();
//        int dotCount = 0;
//        for (char c : additionalInfo.toCharArray()) {
//            formattedInfo.append(c);
//            if (c == '.') {
//                dotCount++;
//                if (dotCount % 3 == 0) {
//                    formattedInfo.append("<br/>");
//                }
//            }
//        }
//
//        return formattedInfo.toString();
//    }
//}
//
