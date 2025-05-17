package com.hiearth.fullquiz.global.exception;

import com.hiearth.fullquiz.global.error.ErrorType;
import lombok.Getter;

@Getter
public class FullquizException extends RuntimeException{
    private final ErrorType errorType;

    public FullquizException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}
