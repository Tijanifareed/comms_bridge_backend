package com.capstone.africa.semicolon.comms_bridge.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponse {
    private Object datas;
    private boolean isSucessfull;
    public boolean isSucessfull() {
        return isSucessfull;
    }

    public void setSucessfull(boolean sucessfull) {
        isSucessfull = sucessfull;
    }

    public ApiResponse(boolean isSucessfull, Object datas) {
        this.isSucessfull = isSucessfull;
        this.datas = datas;
    }
}

