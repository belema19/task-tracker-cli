package com.tasktracker.exception;

import java.util.Arrays;

public class UnknownArg extends Exception {
    public UnknownArg(String[] args) {
        super(
                String.format("Unknown args '%s'", Arrays.toString(args))
        );
    }
}
