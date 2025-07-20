package com.atm.mapper;

import com.atm.dto.ATMStatusDTO;
import com.atm.util.DateTimeUtil;
import java.time.LocalDateTime;
import java.util.Map;

public class ATMMapper {

    public static ATMStatusDTO createATMStatusDTO(int totalCash, Map<Integer, Integer> denominationCount) {
        return new ATMStatusDTO(
            totalCash,
            denominationCount,
            totalCash > 0 ? "OPERATIONAL" : "OUT_OF_SERVICE",
            DateTimeUtil.formatDateTime(LocalDateTime.now())
        );
    }
}