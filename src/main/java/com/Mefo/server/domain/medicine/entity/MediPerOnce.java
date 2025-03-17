package com.Mefo.server.domain.medicine.entity;

public enum MediPerOnce {
    ONE("1회 1정"),
    TWO("1회 2정"),
    THREE("1회 3정"),
    ETC("기타");

    private String number;

    MediPerOnce(String number){this.number=number;}
}
