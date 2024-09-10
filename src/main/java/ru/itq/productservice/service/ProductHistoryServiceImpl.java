package ru.itq.productservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import ru.itq.productservice.entity.Product;

import java.util.List;

@Service
@Slf4j
public class ProductHistoryServiceImpl implements ProductHistoryService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Object[]> getProductRevisions(Long productId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        List<Object[]> revisions = auditReader.createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.id().eq(productId))
                .getResultList();

        log.info("Revisions for product with ID {}: {}", productId, revisions);

        return revisions;

    }

}
