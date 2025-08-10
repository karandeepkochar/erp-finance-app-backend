package com.finpilot.erp_ap.enums;

public enum VendorBillStatus {

    DRAFT,               // Invoice being created
    PENDING_APPROVAL,    // Awaiting internal approval
    APPROVED,            // Approved internally, ready to send
    SENT,                // Sent to customer
    PARTIALLY_PAID,      // Some payment received
    PAID,                // Fully paid
    OVERDUE,             // Past due date, unpaid
    VOID,                // Cancelled or replaced
    WRITTEN_OFF          // Considered uncollectible
}
