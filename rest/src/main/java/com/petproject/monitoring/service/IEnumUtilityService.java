package com.petproject.monitoring.service;

import com.petproject.monitoring.sort.EnumGetter;

public interface IEnumUtilityService {
    <E extends Enum<E> & EnumGetter> String getParamIfValid(Class<E> enumToCheck, String criteria);
}
