package com.example.orderprocessing.exceptions;

public class DataBaseException extends RuntimeException {
    public DataBaseException(String excceptionMessage) {
        super(excceptionMessage);
    }
}
