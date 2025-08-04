package com.finpilot.erp_ar.service.impl;

import com.finpilot.erp_ar.dto.InvoiceLineItemResponseDTO;
import com.finpilot.erp_ar.dto.InvoiceRequestDTO;
import com.finpilot.erp_ar.dto.InvoiceResponseDTO;
import com.finpilot.erp_ar.entity.Customer;
import com.finpilot.erp_ar.entity.Invoice;
import com.finpilot.erp_ar.entity.InvoiceLineItem;
import com.finpilot.erp_ar.enums.InvoiceStatus;
import com.finpilot.erp_ar.repository.CustomerRepository;
import com.finpilot.erp_ar.repository.InvoiceRepository;
import com.finpilot.erp_ar.service.InvoiceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @PersistenceContext
    private EntityManager entityManager;

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }
    @Override
    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO invoiceRequestDTO) {

        // 1. Fetch the customer
        Customer customer = customerRepository.findById(invoiceRequestDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + invoiceRequestDTO.getCustomerId()));

        // 2. Map request to invoice entity
        Invoice invoice = Invoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .customer(customer)
                .invoiceDate(invoiceRequestDTO.getInvoiceDate())
                .dueDate(invoiceRequestDTO.getDueDate())
                .currency(invoiceRequestDTO.getCurrency())
                .remarks(invoiceRequestDTO.getRemarks())
                .status(InvoiceStatus.DRAFT) // Initial status
                .build();

        // 3. Create and attach line items
        List<InvoiceLineItem> lineItems = invoiceRequestDTO.getLineItems().stream()
                .map(itemDTO -> InvoiceLineItem.builder()
                        .invoice(invoice) // set parent
                        .description(itemDTO.getDescription())
                        .quantity(itemDTO.getQuantity())
                        .unitPrice(itemDTO.getUnitPrice())
                        .lineTotal(itemDTO.getLineTotal())
                        .build())
                .collect(Collectors.toList());

        invoice.setLineItems(lineItems);

        // 4. Calculate total amount (or use provided one if trusted)
        BigDecimal totalAmount = lineItems.stream()
                .map(InvoiceLineItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        invoice.setTotalAmount(totalAmount);

        // 5. Save invoice (cascades to line items)
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // 6. Map to response DTO
        List<InvoiceLineItemResponseDTO> lineItemResponses = savedInvoice.getLineItems().stream()
                .map(item -> InvoiceLineItemResponseDTO.builder()
                        .invoiceLineItemid(item.getInvoiceLineItemid())
                        .description(item.getDescription())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .lineTotal(item.getLineTotal())
                        .build())
                .collect(Collectors.toList());

        return InvoiceResponseDTO.builder()
                .invoiceId(savedInvoice.getInvoiceId())
                .invoiceNumber(savedInvoice.getInvoiceNumber())
                .customerId(savedInvoice.getCustomer().getCustomerId())
                .invoiceDate(savedInvoice.getInvoiceDate())
                .dueDate(savedInvoice.getDueDate())
                .totalAmount(savedInvoice.getTotalAmount())
                .status(savedInvoice.getStatus())
                .remarks(savedInvoice.getRemarks())
                .lineItems(lineItemResponses)
                .build();
    }

    private String generateInvoiceNumber() {
        Long nextVal = ((Number) entityManager.createNativeQuery("SELECT nextval('seq_invoice_number')")
                .getSingleResult()).longValue();
        int currentYear = LocalDate.now().getYear();
        return "INV-" + currentYear + "-" +String.format("%04d", nextVal); // CUST-0001
    }
}
