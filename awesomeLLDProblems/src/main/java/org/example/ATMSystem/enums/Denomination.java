package org.example.ATMSystem.enums;

public enum Denomination {
    HUNDRED(100),
    FIFTY(50),
    TWENTY(20),
    TEN(10);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}