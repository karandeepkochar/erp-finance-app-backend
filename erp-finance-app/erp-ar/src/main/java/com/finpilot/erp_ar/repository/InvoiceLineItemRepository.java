package com.finpilot.erp_ar.repository;

import com.finpilot.erp_ar.entity.InvoiceLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceLineItemRepository extends JpaRepository<InvoiceLineItem, Long> {
    @Modifying
    @Query("DELETE FROM InvoiceLineItem ili WHERE ili.invoice.invoiceId = :invoiceId")
    void deleteByInvoiceId(@Param("invoiceId") Long invoiceId);
}
