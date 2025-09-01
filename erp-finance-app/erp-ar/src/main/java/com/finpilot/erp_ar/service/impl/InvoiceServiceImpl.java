package com.finpilot.erp_ar.service.impl;

import com.finpilot.erp_ar.dto.InvoiceLineItemResponseDTO;
import com.finpilot.erp_ar.dto.InvoiceRequestDTO;
import com.finpilot.erp_ar.dto.InvoiceResponseDTO;
import com.finpilot.erp_ar.dto.InvoiceUpdateRequestDTO;
import com.finpilot.erp_ar.entity.Customer;
import com.finpilot.erp_ar.entity.Invoice;
import com.finpilot.erp_ar.entity.InvoiceLineItem;
import com.finpilot.erp_ar.enums.InvoiceStatus;
import com.finpilot.erp_ar.repository.CustomerRepository;
import com.finpilot.erp_ar.repository.InvoiceLineItemRepository;
import com.finpilot.erp_ar.repository.InvoiceRepository;
import com.finpilot.erp_ar.service.InvoiceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final InvoiceLineItemRepository invoiceLineItemRepository;
    private final ModelMapper modelMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, CustomerRepository customerRepository, InvoiceLineItemRepository invoiceLineItemRepository, ModelMapper modelMapper) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.invoiceLineItemRepository = invoiceLineItemRepository;
        this.modelMapper = modelMapper;
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
                .createdAt(savedInvoice.getCreatedAt())
                .updatedAt(savedInvoice.getUpdatedAt())
                .build();
    }

    @Transactional
    public InvoiceResponseDTO updateInvoice(InvoiceUpdateRequestDTO dto) {

        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

        // Validate status transition
        validateStatusChange(invoice.getStatus(), dto.getStatus());

        // If invoice is APPROVED — allow only remarks/status change
        if (invoice.getStatus() == InvoiceStatus.APPROVED) {
            if (dto.getRemarks() != null) invoice.setRemarks(dto.getRemarks());
            if (dto.getStatus() != null) invoice.setStatus(dto.getStatus());
            // No other field changes
        }
        else if (invoice.getStatus() == InvoiceStatus.DRAFT) {
            // Update basic fields if not null
            if (dto.getInvoiceDate() != null) invoice.setInvoiceDate(dto.getInvoiceDate());
            if (dto.getDueDate() != null) invoice.setDueDate(dto.getDueDate());
            if (dto.getTotalAmount() != null) invoice.setTotalAmount(dto.getTotalAmount());
            if (dto.getRemarks() != null) invoice.setRemarks(dto.getRemarks());
            if (dto.getStatus() != null) invoice.setStatus(dto.getStatus());

            // Line items update (simple replace for MVP)
            if (dto.getLineItems() != null && !dto.getLineItems().isEmpty()) {
                invoice.getLineItems().clear();
                List<InvoiceLineItem> newItems = dto.getLineItems().stream()
                        .map(li -> modelMapper.map(li, InvoiceLineItem.class))
                        .peek(li -> li.setInvoice(invoice))
                        .collect(Collectors.toList());
                invoice.getLineItems().addAll(newItems);
            }
        }

        Invoice saved = invoiceRepository.save(invoice);
        return modelMapper.map(saved, InvoiceResponseDTO.class);
    }

    private void validateStatusChange(InvoiceStatus current, InvoiceStatus requested) {
        if (requested == null) return;

        switch (current) {
            case DRAFT -> {
                if (!(requested.equals(InvoiceStatus.APPROVED) || requested.equals(InvoiceStatus.VOID) || requested.equals(InvoiceStatus.DRAFT))) {
                    throw new IllegalArgumentException("Invalid status change from DRAFT");
                }
            }
            case APPROVED -> {
                if (!(requested.equals(InvoiceStatus.VOID) || requested.equals(InvoiceStatus.APPROVED))) {
                    throw new IllegalArgumentException("Invalid status change from APPROVED");
                }
            }
            case VOID -> {
                throw new IllegalArgumentException("Cancelled invoices cannot be modified");
            }
        }
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(invoice -> modelMapper.map(invoice, InvoiceResponseDTO.class))
                .collect(Collectors.toList());
    }

    private String generateInvoiceNumber() {
        Long nextVal = ((Number) entityManager.createNativeQuery("SELECT nextval('seq_invoice_number')")
                .getSingleResult()).longValue();
        int currentYear = LocalDate.now().getYear();
        return "INV-" + currentYear + "-" +String.format("%04d", nextVal); // CUST-0001
    }
}
