package com.finpilot.erp_ar.controller;

import com.finpilot.erp_ar.dto.*;
import com.finpilot.erp_ar.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<InvoiceResponseDTO>> createInvoice
            (@Valid @RequestBody InvoiceRequestDTO invoiceRequestDTO) {
        InvoiceResponseDTO InvoiceResponseDTO = invoiceService.createInvoice(invoiceRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<InvoiceResponseDTO>builder()
                        .success(true)
                        .message("Invoice created successfully")
                        .data(InvoiceResponseDTO)
                        .timestamp(Instant.now())
                        .build()
        );
    }
}
