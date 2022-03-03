package com.company.services;

import com.company.models.Type;

public class TypeService {
    public boolean checkNeedUpdate(Type oldType, Type newType) {
        return !oldType.equals(newType);
    }
}
