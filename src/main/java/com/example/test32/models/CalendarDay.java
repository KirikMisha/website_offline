package com.example.test32.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CalendarDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private int day;
    private int month;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo1;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo2;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo3;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo4;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo5;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo6;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo7;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo8;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo9;
    @Column(columnDefinition = "LONGTEXT")
    private String additionalInfo10;

    public List<String> getAdditionalInfoList() {
        List<String> additionalInfoList = new ArrayList<>();
        if (additionalInfo1 != null) {
            additionalInfoList.add(additionalInfo1);
        }
        if (additionalInfo2 != null) {
            additionalInfoList.add(additionalInfo2);
        }
        if (additionalInfo3 != null) {
            additionalInfoList.add(additionalInfo3);
        }
        if (additionalInfo4 != null) {
            additionalInfoList.add(additionalInfo4);
        }
        if (additionalInfo5 != null) {
            additionalInfoList.add(additionalInfo5);
        }
        if (additionalInfo6 != null) {
            additionalInfoList.add(additionalInfo6);
        }
        if (additionalInfo7 != null) {
            additionalInfoList.add(additionalInfo7);
        }
        if (additionalInfo8 != null) {
            additionalInfoList.add(additionalInfo8);
        }
        if (additionalInfo9 != null) {
            additionalInfoList.add(additionalInfo9);
        }
        if (additionalInfo10 != null) {
            additionalInfoList.add(additionalInfo10);
        }
        return additionalInfoList;
    }


    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation1;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation2;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation3;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation4;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation5;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation6;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation7;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation8;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String internationalInformation9;

}
