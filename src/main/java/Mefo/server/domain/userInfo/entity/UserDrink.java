package Mefo.server.domain.userInfo.entity;

public enum UserDrink {
    RARELY("0~1회"),
    SOMETIMES("2~3회"),
    OFTEN("4~5회"),
    ALWAYS("6~7회");

    private String times;

    UserDrink(String times){this.times=times;}
}
