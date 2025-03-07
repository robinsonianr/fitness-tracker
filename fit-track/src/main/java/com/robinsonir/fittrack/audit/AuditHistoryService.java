package com.robinsonir.fittrack.audit;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuditHistoryService {

    private final AuditReader auditReader;

    public AuditHistoryService(AuditReader auditReader) {
        this.auditReader = auditReader;
    }

    public Map<Integer, OffsetDateTime> getCustomerWeightHistory(Long entityId) {
        Map<Integer, OffsetDateTime> weightAudit = new HashMap<>();
        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(CustomerEntity.class, true,true)
                .add(AuditEntity.id().eq(entityId))
                .add(AuditEntity.property("weight").hasChanged());

        List items = auditQuery.getResultList();
        for (Object item : items) {
            CustomerEntity customer = (CustomerEntity) item;
            weightAudit.put(customer.getWeight(), customer.getLastModifiedDate());
        }

        return weightAudit;
    }

}
