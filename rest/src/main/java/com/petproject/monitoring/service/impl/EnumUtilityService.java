package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.exception.InvalidParameterException;
import com.petproject.monitoring.service.IEnumUtilityService;
import com.petproject.monitoring.sort.EnumGetter;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class EnumUtilityService implements IEnumUtilityService {
    @Override
    public <E extends Enum<E> & EnumGetter> String getParamIfValid(Class<E> enumToCheck, String criteria) {
        for (E value : EnumSet.allOf(enumToCheck)) {
            if(value.name().equalsIgnoreCase(criteria)) {
                return value.getValue();
            }
        }
        throw new InvalidParameterException();
    }
}
