package com.capstone.africa.semicolon.comms_bridge.exception;

public class CommsBridgeException extends RuntimeException {
    public CommsBridgeException(String response) {
        super(response);
    }
}
