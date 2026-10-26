package org.example.ATMSystem;

import org.example.ATMSystem.enums.Denomination;

import java.util.Map;

public interface CashHandler {
    void setNextHandler(CashHandler handler);
    void dispense(int amount, Map<Denomination, Integer> result);
}
