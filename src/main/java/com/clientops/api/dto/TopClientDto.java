package com.clientops.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TopClientDto {
    private String name;
    private long count;
}
