package com.finpilot.erp_ar.service;

import com.finpilot.erp_ar.dto.*;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO updateInvoice(InvoiceUpdateRequestDTO updateRequestDTO);
    List<InvoiceResponseDTO> getAllInvoices();
}
