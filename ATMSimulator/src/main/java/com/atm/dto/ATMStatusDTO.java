package com.atm.dto;

import java.util.Map;

public class ATMStatusDTO {

    private int totalCash;
    private Map<Integer, Integer> denominationCount;
    private String status;
    private String lastUpdated;

    public ATMStatusDTO() {}

    public ATMStatusDTO(int totalCash, Map<Integer, Integer> denominationCount, 
                       String status, String lastUpdated) {
        this.totalCash = totalCash;
        this.denominationCount = denominationCount;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public int getTotalCash() { return totalCash; }
    public void setTotalCash(int totalCash) { this.totalCash = totalCash; }

    public Map<Integer, Integer> getDenominationCount() { return denominationCount; }
    public void setDenominationCount(Map<Integer, Integer> denominationCount) { 
        this.denominationCount = denominationCount; 
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
}