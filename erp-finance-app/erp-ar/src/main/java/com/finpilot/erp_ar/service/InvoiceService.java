package com.finpilot.erp_ar.service;

import com.finpilot.erp_ar.dto.CustomerApproveRequestDTO;
import com.finpilot.erp_ar.dto.CustomerRequestDTO;
import com.finpilot.erp_ar.dto.InvoiceRequestDTO;
import com.finpilot.erp_ar.dto.InvoiceResponseDTO;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(InvoiceRequestDTO invoiceRequestDTO);
}
