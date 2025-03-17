package com.Mefo.server.domain.medicine.entity;

public enum MediDoseTime {
    FAST("공복"),
    BEFORE("식전"),
    BETWEEN("식간"),
    AFTER("식후");

    private String time;

    MediDoseTime(String time){this.time=time;}
}
