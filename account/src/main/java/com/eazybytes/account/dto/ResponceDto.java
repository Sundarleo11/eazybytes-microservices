package com.eazybytes.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponceDto {

    private String statusCode;

    private String statusMessage;
}
