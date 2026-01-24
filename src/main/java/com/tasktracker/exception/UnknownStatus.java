package com.tasktracker.exception;

public class UnknownStatus extends Exception {
    public UnknownStatus(String status) {
        super(
                String.format("Unknown status '%s'", status)
        );
    }
}
