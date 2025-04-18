package com.finpilot.erp_ar.service;

import com.finpilot.erp_ar.dto.CustomerApproveRequestDTO;
import com.finpilot.erp_ar.dto.CustomerRequestDTO;
import com.finpilot.erp_ar.dto.CustomerResponseDTO;
import org.springframework.stereotype.Service;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);
    CustomerResponseDTO approveCustomer(CustomerApproveRequestDTO customerApproveRequestDTO);
    CustomerResponseDTO rejectCustomer(CustomerApproveRequestDTO customerApproveRequestDTO);
}
