package com.cs21.csp;

import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@RequiredArgsConstructor
public class CspResourceService {

    private final EntityManagerFactory entityManagerFactory;

    public List<CsCspResources> getAll() {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();
        List<CsCspResources> resourcesList = null;

        try {
            transaction.begin();

            resourcesList = entityManager.createQuery("SELECT csp FROM CsCspResources csp", CsCspResources.class).getResultList();

            transaction.commit();
        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }

        return resourcesList;
    }

    public void register(List<CsCspResources> cspResourcesList) {
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.createQuery("DELETE FROM CsCspResources csp").executeUpdate();

            for (CsCspResources csCspResources : cspResourcesList)
                entityManager.persist(csCspResources);

            transaction.commit();
        } catch (IllegalArgumentException e) {
            if (transaction.isActive()) transaction.rollback();

        } finally {
            entityManager.close();

        }
    }
}