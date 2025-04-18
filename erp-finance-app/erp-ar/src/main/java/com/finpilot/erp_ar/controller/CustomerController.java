package com.finpilot.erp_ar.controller;

import com.finpilot.erp_ar.dto.ApiResponseWrapper;
import com.finpilot.erp_ar.dto.CustomerApproveRequestDTO;
import com.finpilot.erp_ar.dto.CustomerRequestDTO;
import com.finpilot.erp_ar.dto.CustomerResponseDTO;
import com.finpilot.erp_ar.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/ping")
    public String pingCheck(){
        return "OK";
    }

    @PostMapping
    public ResponseEntity<ApiResponseWrapper<CustomerResponseDTO>> createCustomer
            (@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO customerResponseDTO = customerService.createCustomer(customerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<CustomerResponseDTO>builder()
                        .success(true)
                        .message("Customer created successfully")
                        .data(customerResponseDTO)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PostMapping(path = "/approve")
    public ResponseEntity<ApiResponseWrapper<CustomerResponseDTO>> approveCustomer
            (@RequestBody CustomerApproveRequestDTO customerApproveRequestDTO) {
        CustomerResponseDTO customerResponseDTO = customerService.approveCustomer(customerApproveRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<CustomerResponseDTO>builder()
                        .success(true)
                        .message("Customer approved successfully")
                        .data(customerResponseDTO)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PostMapping(path = "/reject")
    public ResponseEntity<ApiResponseWrapper<CustomerResponseDTO>> rejectCustomer
            (@RequestBody CustomerApproveRequestDTO customerApproveRequestDTO) {
        CustomerResponseDTO customerResponseDTO = customerService.rejectCustomer(customerApproveRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseWrapper.<CustomerResponseDTO>builder()
                        .success(true)
                        .message("Customer Rejected successfully")
                        .data(customerResponseDTO)
                        .timestamp(Instant.now())
                        .build()
        );
    }


}
