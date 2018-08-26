package com.petproject.monitoring.sort;

import lombok.Getter;

@Getter
public enum TwitterSortKeys implements EnumGetter {
    DATE("createdAtTwitter"), FAV("favouritesCount"), SHARE("retweetCount");

    private String value;

    TwitterSortKeys(String value) {
        this.value = value;
    }
}
