package com.petproject.monitoring.sort;

import lombok.Getter;

@Getter
public enum SortDirection implements EnumGetter {
    DESC("desc"), ASC("asc");

    private String value;

    SortDirection(String value) {
        this.value = value;
    }
}
