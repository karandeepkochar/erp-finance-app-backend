package com.finpilot.erp_ar.repository;

import com.finpilot.erp_ar.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
