package org.example.enums;

public enum StatusCode {
    OK(200),
    CREATE(201);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
