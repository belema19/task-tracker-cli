package com.tasktracker.exception;

public class NoArgsProvided extends Exception {
    public NoArgsProvided() {
        super(
                """
                No Args Provided to the CLI.
                Use: task-cli <command> [options...]
                """
        );
    }
}
