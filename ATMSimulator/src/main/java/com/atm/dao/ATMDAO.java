package com.atm.dao;

import java.util.Map;

public interface ATMDAO {
    boolean updateCashInventory(int denomination, int count);
    Map<Integer, Integer> getCashInventory();
    int getTotalCash();
    boolean addCash(int denomination, int count);
    boolean removeCash(int denomination, int count);
}