package com.capstone.africa.semicolon.comms_bridge.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse {
    private Object data;
    private boolean isSucessfull;
    public boolean isSucessfull() {
        return isSucessfull;
    }

    public void setSucessfull(boolean sucessfull) {
        isSucessfull = sucessfull;
    }

    public ApiResponse(boolean isSucessfull, Object data) {
        this.isSucessfull = isSucessfull;
        this.data = data;
    }
}

