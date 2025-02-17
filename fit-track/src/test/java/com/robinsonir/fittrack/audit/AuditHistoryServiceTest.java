package com.robinsonir.fittrack.audit;

import com.robinsonir.fittrack.data.entity.customer.CustomerEntity;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditHistoryServiceTest {

    @Mock
    private AuditReader auditReader;

    @InjectMocks
    private AuditHistoryService auditHistoryService;

    @BeforeEach
    void setUp() {
        auditHistoryService = new AuditHistoryService(auditReader);
    }

    @Test
    void testGetCustomerWeightAuditHistory() {
        Long entityId = 1L;
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setWeight(70);
        customer1.setLastModifiedDate(OffsetDateTime.parse("2024-01-01T10:00:00Z"));

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setWeight(75);
        customer2.setLastModifiedDate(OffsetDateTime.parse("2024-02-01T10:00:00Z"));

        List<CustomerEntity> mockResults = Arrays.asList(customer1, customer2);
        AuditQueryCreator auditQueryCreator = Mockito.mock(AuditQueryCreator.class);
        AuditQuery auditQuery = Mockito.mock(AuditQuery.class);

        when(auditReader.createQuery()).thenReturn(auditQueryCreator);
        when(auditQueryCreator.forRevisionsOfEntity(CustomerEntity.class, true, true))
                .thenReturn(auditQuery);
        when(auditQuery.add(any(AuditCriterion.class))).thenReturn(auditQuery);
        when(auditQuery.getResultList()).thenReturn(mockResults);

        Map<Integer, OffsetDateTime> result = auditHistoryService.getCustomerWeightAuditHistory(entityId);

        Map<Integer, OffsetDateTime> expected = new HashMap<>();
        expected.put(70, OffsetDateTime.parse("2024-01-01T10:00:00Z"));
        expected.put(75, OffsetDateTime.parse("2024-02-01T10:00:00Z"));

        assertEquals(expected, result);
    }
}
