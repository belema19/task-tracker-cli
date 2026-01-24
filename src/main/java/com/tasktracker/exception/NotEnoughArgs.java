package com.tasktracker.exception;

public class NotEnoughArgs extends Exception {
    public NotEnoughArgs(String action) {
        super(
                String.format("Not enough args provided for '%s' action.", action)
        );
    }
}
