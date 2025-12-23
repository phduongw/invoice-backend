package com.light.invoices.entities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum PayoutStatus {

    PENDING("PENDING"),
    PROCESSING("PROCESSING"),
    PAID("PAID");

    final String name;

}
