package com.finpilot.erp_ar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class ApiResponseWrapper<T> {
    private boolean success;
    private String message;
    private T data;
    private Map<String, String> errors;
    private Instant timestamp;
}
