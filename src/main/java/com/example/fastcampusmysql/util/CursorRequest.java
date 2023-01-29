package com.example.fastcampusmysql.util;

public record CursorRequest(Long key, int size) {
    public final static Long NONE_KEY = -1L;

    public Boolean hasKey(){
        return key != null;
    }

    public CursorRequest next(Long ket){
        return new CursorRequest(key, size);
    }
}
