package com.robinsonir.fittrack.api;

import com.robinsonir.fittrack.audit.AuditHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
@RequestMapping("api/v1/audit")
public class AuditHistoryController {

    private final AuditHistoryService auditHistoryService;

    public AuditHistoryController(AuditHistoryService auditHistoryService) {
        this.auditHistoryService = auditHistoryService;
    }

    @GetMapping("{entityId}")
    public ResponseEntity<Map<Integer, OffsetDateTime>> getCustomerWeightAuditHistory(@PathVariable("entityId") Long entityId) {
        return ResponseEntity.ok(auditHistoryService.getCustomerWeightHistory(entityId));
    }
}
