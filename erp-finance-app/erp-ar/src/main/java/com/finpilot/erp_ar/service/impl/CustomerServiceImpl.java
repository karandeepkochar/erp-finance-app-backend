package com.finpilot.erp_ar.service.impl;

import com.finpilot.erp_ar.dto.CustomerApproveRequestDTO;
import com.finpilot.erp_ar.dto.CustomerRequestDTO;
import com.finpilot.erp_ar.dto.CustomerResponseDTO;
import com.finpilot.erp_ar.entity.Customer;
import com.finpilot.erp_ar.entity.CustomerStatusAudit;
import com.finpilot.erp_ar.enums.CustomerStatus;
import com.finpilot.erp_ar.repository.CustomerRepository;
import com.finpilot.erp_ar.repository.CustomerStatusAuditRepository;
import com.finpilot.erp_ar.service.CustomerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl implements CustomerService {

    @PersistenceContext
    private EntityManager entityManager;

    private final CustomerRepository customerRepository;

    private final CustomerStatusAuditRepository customerStatusAuditRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerStatusAuditRepository customerStatusAuditRepository) {
        this.customerRepository = customerRepository;
        this.customerStatusAuditRepository = customerStatusAuditRepository;
    }

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {

        Customer customer = Customer.builder()
                .name(customerRequestDTO.getName())
                .customerCode(generateCustomerCode())
                .contactPerson(customerRequestDTO.getContactPerson())
                .contactEmail(customerRequestDTO.getContactEmail())
                .contactPhone(customerRequestDTO.getContactPhone())
                .altPhone(customerRequestDTO.getAltPhone())
                .email(customerRequestDTO.getEmail())
                .phone(customerRequestDTO.getPhone())
                .taxId(customerRequestDTO.getTaxId())
                .billingAddress(customerRequestDTO.getBillingAddress())
                .shippingAddress(customerRequestDTO.getShippingAddress())
                .paymentTerms(customerRequestDTO.getPaymentTerms())
                .status(CustomerStatus.PENDING.name())
                .notes(customerRequestDTO.getNotes())
                .creditLimit(customerRequestDTO.getCreditLimit())
                .balanceDue(BigDecimal.ZERO)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        CustomerResponseDTO response = CustomerResponseDTO.builder()
                .customerId(savedCustomer.getCustomerId())
                .name(savedCustomer.getName())
                .customerCode(savedCustomer.getCustomerCode())
                .contactEmail(savedCustomer.getContactEmail())
                .status(savedCustomer.getStatus())
                .balanceDue(savedCustomer.getBalanceDue())
                .build();

        return response;
    }

    public CustomerResponseDTO approveCustomer(CustomerApproveRequestDTO customerApproveRequestDTO) {
        Customer customer = customerRepository.findById(customerApproveRequestDTO.getCustomerId()).orElseThrow(
                () -> new RuntimeException("Customer with id " + customerApproveRequestDTO.getCustomerId() + " not found")
        );
        if (!customer.getStatus().equals("PENDING")) {
            throw new RuntimeException("Customer is not in PENDING status");
        }
        String previousStatus = customer.getStatus();

        customer.setStatus(CustomerStatus.APPROVED.name());
        customer.setApprovedBy(customerApproveRequestDTO.getApprovedBy());
        customer.setApprovedAt(LocalDateTime.now());

        Customer savedCustomer = customerRepository.save(customer);
        //Audit Entry
        CustomerStatusAudit audit = CustomerStatusAudit.builder()
                .customer(savedCustomer)
                .previousStatus(previousStatus)
                .newStatus(savedCustomer.getStatus())
                .updatedBy(customerApproveRequestDTO.getApprovedBy())
                .updatedAt(LocalDateTime.now())
                .remarks(customerApproveRequestDTO.getRemarks())
                .build();
        customerStatusAuditRepository.save(audit);

        return CustomerResponseDTO.builder()
                .customerId(savedCustomer.getCustomerId())
                .name(savedCustomer.getName())
                .customerCode(savedCustomer.getCustomerCode())
                .contactEmail(savedCustomer.getContactEmail())
                .status(savedCustomer.getStatus())
                .approvedAt(savedCustomer.getApprovedAt())
                .approvedBy(savedCustomer.getApprovedBy())
                .build();
    }

    public CustomerResponseDTO rejectCustomer(CustomerApproveRequestDTO customerApproveRequestDTO) {
        Customer customer = customerRepository.findById(customerApproveRequestDTO.getCustomerId()).orElseThrow(
                () -> new RuntimeException("Customer with id " + customerApproveRequestDTO.getCustomerId() + " not found")
        );
        if (!customer.getStatus().equals("PENDING")) {
            throw new RuntimeException("Customer is not in PENDING status");
        }
        String previousStatus = customer.getStatus();

        customer.setStatus(CustomerStatus.REJECTED.name());
        Customer savedCustomer = customerRepository.save(customer);
        //Audit Entry
        CustomerStatusAudit audit = CustomerStatusAudit.builder()
                .customer(savedCustomer)
                .previousStatus(previousStatus)
                .newStatus(savedCustomer.getStatus())
                .updatedBy(customerApproveRequestDTO.getApprovedBy())
                .updatedAt(LocalDateTime.now())
                .remarks(customerApproveRequestDTO.getRemarks())
                .build();
        customerStatusAuditRepository.save(audit);

        return CustomerResponseDTO.builder()
                .customerId(savedCustomer.getCustomerId())
                .name(savedCustomer.getName())
                .customerCode(savedCustomer.getCustomerCode())
                .contactEmail(savedCustomer.getContactEmail())
                .status(savedCustomer.getStatus())
                .approvedAt(savedCustomer.getApprovedAt())
                .approvedBy(savedCustomer.getApprovedBy())
                .build();
    }

    public String generateCustomerCode(){
        Long nextVal = ((Number) entityManager.createNativeQuery("SELECT nextval('seq_customer_code')")
                .getSingleResult()).longValue();
        return "CUST-" + String.format("%04d", nextVal); // CUST-0001
    }
}
