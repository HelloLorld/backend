package com.company.services;

import com.company.models.Type;

public class TypeService {
    public boolean checkNeedUpdate(Type oldType, Type newType) {
        boolean needChange = false;
        if (!oldType.getType().equals(newType.getType())) {
            needChange = true;
        }
        return needChange;
    }
}
