package com.ucx.training.shop.type;

import java.time.LocalDateTime;
import java.time.Month;

public enum Quartal {
    FIRST_QUARTAL(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY, 1, 0, 0),
            LocalDateTime.of(LocalDateTime.now().getYear(), Month.MARCH, 31, 23, 59, 59)),
    SECOND_QUARTAL(LocalDateTime.of(LocalDateTime.now().getYear(), Month.APRIL, 1, 0, 0),
            LocalDateTime.of(LocalDateTime.now().getYear(), Month.JUNE, 30, 23, 59, 59)),
    THIRD_QUARTAL(LocalDateTime.of(LocalDateTime.now().getYear(), Month.JULY, 1, 0, 0),
            LocalDateTime.of(LocalDateTime.now().getYear(), Month.SEPTEMBER, 30, 23, 59, 59)),
    FOURTH_QUARTAL(LocalDateTime.of(LocalDateTime.now().getYear(), Month.OCTOBER, 1, 0, 0),
            LocalDateTime.of(LocalDateTime.now().getYear(), Month.DECEMBER, 31, 23, 59, 59));

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    Quartal(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
