package com.finpilot.erp_ar.controller;

import com.finpilot.erp_ar.dto.*;
import com.finpilot.erp_ar.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

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
        InvoiceResponseDTO invoiceResponseDTO = invoiceService.createInvoice(invoiceRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<InvoiceResponseDTO>builder()
                        .success(true)
                        .message("Invoice created successfully")
                        .data(invoiceResponseDTO)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<InvoiceResponseDTO>> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceUpdateRequestDTO updateDTO) {
        updateDTO.setInvoiceId(id); // Ensure consistency with path
        InvoiceResponseDTO updatedInvoice = invoiceService.updateInvoice(updateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<InvoiceResponseDTO>builder()
                        .success(true)
                        .message("Invoice updated successfully")
                        .data(updatedInvoice)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<InvoiceResponseDTO>>> getAllInvoices() {
        List<InvoiceResponseDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponseWrapper.<List<InvoiceResponseDTO>>builder()
                        .success(true)
                        .message("Invoice fetched successfully")
                        .data(invoices)
                        .timestamp(Instant.now())
                        .build()
        );
    }
}
