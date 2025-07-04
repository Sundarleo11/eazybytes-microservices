package com.eazybytes.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema to hold ErrorResponse information"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "apiPath in the response"
    )
    private String apiPath;

    @Schema(
            description = "ErrorCode in the response"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "errorMessage in the response"
    )
    private String errorMessage;

    @Schema(
            description = "errorTime in the response"
    )
    private LocalDateTime errorTime;

}