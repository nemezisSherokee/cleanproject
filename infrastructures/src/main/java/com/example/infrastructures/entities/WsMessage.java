package com.example.infrastructures.entities;

import lombok.Data;

@Data
public class WsMessage {
    private String from;
    private String text;
    private String deviceId;

}
