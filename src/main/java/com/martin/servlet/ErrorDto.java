package com.martin.servlet;

import java.util.Objects;

public class ErrorDto {
    private String error;

    public ErrorDto(String error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDto errorDto = (ErrorDto) o;
        return Objects.equals(error, errorDto.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(error);
    }
}
